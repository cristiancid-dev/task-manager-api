package com.cristiancid.taskmanager.controller;

import com.cristiancid.taskmanager.dto.CreateUserRequest;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        User newUser = userService.createUser(name, email);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
