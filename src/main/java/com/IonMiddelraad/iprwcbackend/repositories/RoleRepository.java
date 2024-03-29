package com.IonMiddelraad.iprwcbackend.repositories;

import com.IonMiddelraad.iprwcbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE Role r SET r.permissions = :permissions, r.description = :description, r.title = :title WHERE r.id = :id")
    void update(String permissions, String description, String title, int id);
}
