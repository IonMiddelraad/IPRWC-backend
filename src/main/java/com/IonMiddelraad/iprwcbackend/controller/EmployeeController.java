package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dao.EmployeeDao;
import com.IonMiddelraad.iprwcbackend.exceptions.ResourceNotFoundException;
import com.IonMiddelraad.iprwcbackend.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeDao employeeDao;

    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @GetMapping("/employee")
    public List<Employee> getAllEmployees() {
        return employeeDao.getAll();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        System.out.printf("here");
        Employee employee = employeeDao.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
        return ResponseEntity.ok().body(employee);
    }

    @PostMapping("/employee")
    public ResponseEntity createEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeDao.saveToDatabase(employee));
    }

//    @PutMapping("/employee/{id}")
//    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long employeeId,
//                                                   @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
//        Employee employee = employeeDao.findById(employeeId)
//                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
//
//        employee.setEmailId(employeeDetails.getEmailId());
//        employee.setLastName(employeeDetails.getLastName());
//        employee.setFirstName(employeeDetails.getFirstName());
//        Employee updatedEmployee = EmployeeDao.saveToDatabase(employee);
//        return ResponseEntity.ok(updatedEmployee);
//    }


    @RequestMapping(value = "/sayhello", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> sayHelloWorld(){
        System.out.printf("here");
        return ResponseEntity.ok().body("HelloWorld");
    }
}
