package com.cristiancid.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {

    @NotBlank(message = "title cannot be blank")
    @Size(min = 3, max = 20, message = "title must be between 3 and 20 characters")
    private String title;


    public CreateTaskRequest() {}

    public CreateTaskRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
