package com.cristiancid.taskmanager.service;

import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email) {
        User newUser = new User(name, email);
        return userRepository.save(newUser);
    }
}
