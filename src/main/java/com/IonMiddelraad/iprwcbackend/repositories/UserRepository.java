package com.IonMiddelraad.iprwcbackend.repositories;

import com.IonMiddelraad.iprwcbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User e SET e.name = :username, e.email = :email, e.password = :password WHERE e.id = :user_id")
    void update(String username, String email, String password, int user_id);

}