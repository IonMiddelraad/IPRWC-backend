package com.IonMiddelraad.iprwcbackend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String permissions;

    public Role() {}

    public Role(String title, String description, String permissions) {
        this.title = title;
        this.description = description;
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String [] getPermissions() {
        return permissions.split(",");
    }

    public void setPermissions(String [] permissions) {
        this.permissions = String.join(",", permissions);
    }
}
