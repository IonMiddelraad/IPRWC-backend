package com.IonMiddelraad.iprwcbackend.controller;

import com.IonMiddelraad.iprwcbackend.dao.UserDAO;
import com.IonMiddelraad.iprwcbackend.dto.UserUpdateDTO;
import com.IonMiddelraad.iprwcbackend.model.ApiResponse;
import com.IonMiddelraad.iprwcbackend.model.User;
import com.IonMiddelraad.iprwcbackend.response.types.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/info")
    public User getInfo(){
        return userDAO.getEmployeeDetails();
    }

    @RequestMapping(value = "{employee_id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateUserRole(@PathVariable("employee_id") int employee_id, @Valid @RequestBody UserUpdateDTO employeeUpdateDTO) {
        //Get the orignal employee and check if its not null
        User originalEmployee = this.userDAO.show(employee_id);
        if(isNull(originalEmployee)){
            return new ApiResponse<>(HttpStatus.NOT_FOUND, new Message("Could not find the employee")).getResponse();
        }

        User employee = employeeUpdateDTO.toUser(originalEmployee, this.userDAO.getEmployeeDetails());

        //Update the role of the employee
        employee = this.userDAO.update(employee);

        return new ApiResponse<>(HttpStatus.OK, employee).getResponse();
    }

}
