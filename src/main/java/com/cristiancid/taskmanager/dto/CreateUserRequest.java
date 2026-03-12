package com.cristiancid.taskmanager.dto;

import jakarta.validation.constraints.*;

public class CreateUserRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 2, max = 15, message = "name must be between 2 an 15 characters")
    @Pattern(regexp = "[A-Za-z]+", message = "name can only contain letters")
    private String name;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "Please enter a valid email address")
    private String email;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String email) {
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
