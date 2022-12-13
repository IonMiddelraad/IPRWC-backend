package com.IonMiddelraad.iprwcbackend.security;

import com.IonMiddelraad.iprwcbackend.model.User;
import com.IonMiddelraad.iprwcbackend.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class UserDetailService implements UserDetailsService {

    private UserRepository userRepo;

    public UserDetailService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> employeeResult = userRepo.findByEmail(email);
        if(employeeResult.isEmpty())
            throw new UsernameNotFoundException("Could not find employee with the following email: " + email);
        User employee = employeeResult.get();
        return new org.springframework.security.core.userdetails.User(
                email,
                employee.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}

