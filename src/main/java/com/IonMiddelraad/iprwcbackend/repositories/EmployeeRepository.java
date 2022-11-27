package com.IonMiddelraad.iprwcbackend.repositories;


import com.IonMiddelraad.iprwcbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}