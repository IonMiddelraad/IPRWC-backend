package com.IonMiddelraad.iprwcbackend.dao;

import com.IonMiddelraad.iprwcbackend.model.Employee;
import org.springframework.stereotype.Component;
import com.IonMiddelraad.iprwcbackend.repositories.EmployeeRepository;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class EmployeeDao {

    private final EmployeeRepository employeeRepository;

    public EmployeeDao(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public ArrayList<Employee> getAll(){
        return (ArrayList<Employee>) this.employeeRepository.findAll();
    }

    public Employee saveToDatabase(Employee employee){
       return this.employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee){
        return this.employeeRepository.save(employee);
    }


    public Optional<Employee> findById(Long employeeId) {
        return this.employeeRepository.findById(employeeId);
    }
}
