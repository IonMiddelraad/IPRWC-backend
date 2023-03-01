package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dto.UserStoreDTO;
import com.IonMiddelraad.iprwcbackend.model.LoginCredentials;
import com.IonMiddelraad.iprwcbackend.model.Role;
import com.IonMiddelraad.iprwcbackend.model.User;
import com.IonMiddelraad.iprwcbackend.repositories.UserRepository;
import com.IonMiddelraad.iprwcbackend.security.JWTUtilization;
import com.IonMiddelraad.iprwcbackend.service.EncryptionService;
import com.IonMiddelraad.iprwcbackend.service.RoleService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserRepository userRepo;
    private JWTUtilization jwtUtil;
    private AuthenticationManager authManager;
    private RoleService roleService;

    public AuthController(UserRepository employeeRepo, JWTUtilization jwtUtil, AuthenticationManager authManager, PasswordEncoder passEncoder, RoleService roleService) {
        this.userRepo = employeeRepo;
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public Map<String, Object> registrationHandler(@Valid @RequestBody UserStoreDTO employeeDTO) throws Exception {
        User employee = employeeDTO.toUser();
        employee.setPassword(EncryptionService.decrypt(employee.getPassword()));

        List<Role> roles = new ArrayList<>();
        int userRoleIndex = 1;
        roles.add(this.roleService.getRoles().get(userRoleIndex));
        employee.setRoles(roles);

        userRepo.save(employee);
        String token = jwtUtil.generateToken(employee.getEmail(), roleService.toJsonRoleString(employee.getRoles()));
        return Collections.singletonMap("jwt_token", token);
    }

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials body) {
        try {
            String email = body.getEmail();
            String password = EncryptionService.decrypt(body.getPassword());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            authManager.authenticate(authToken);
            String token = jwtUtil.generateToken(body.getEmail(), body.getRoles());
            return Collections.singletonMap("jwt_token", token);
        } catch (AuthenticationException authExc) {
            throw new RuntimeException("Login credentials are invalid");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}