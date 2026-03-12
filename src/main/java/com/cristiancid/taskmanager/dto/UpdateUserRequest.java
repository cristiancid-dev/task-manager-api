package com.cristiancid.taskmanager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 2, max = 15, message = "name must be between 2 an 15 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "name can only contain letters")
    private String name;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "Please enter a valid email address")
    private String email;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
