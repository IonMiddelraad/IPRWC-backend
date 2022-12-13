package com.IonMiddelraad.iprwcbackend.dto;

import com.IonMiddelraad.iprwcbackend.Permission;
import com.IonMiddelraad.iprwcbackend.model.Role;
import com.IonMiddelraad.iprwcbackend.model.User;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
public class UserUpdateDTO {

    @Pattern(regexp = "^[A-Za-z1-9 @.]{1,}$")
    @NotNull(message = "The email attribute is required")
    private String email;

    @Pattern(regexp = "^[A-Za-z @.]{1,}$")
    @NotNull(message = "The name attribute is required")
    private String name;

    @NotNull(message = "The password attribute is required")
    private String password;

    private List<Role> roles;

    public User toUser(User employeeToUpdate, User requestEmployee) {
        employeeToUpdate.setEmail(this.email);
        employeeToUpdate.setName(this.name);
        employeeToUpdate.setPassword(this.password);

        if(!roles.isEmpty()) {
            for(Role role: requestEmployee.getRoles()) {
                List<String> permissions = List.of(role.getPermissions());
                if(permissions.contains(String.valueOf(Permission.ADMIN))){
                    employeeToUpdate.setRoles(this.roles);
                }
            }
        }

        return employeeToUpdate;
    }
}
