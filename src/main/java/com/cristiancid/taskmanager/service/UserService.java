package com.cristiancid.taskmanager.service;

import com.cristiancid.taskmanager.dto.UpdateUserRequest;
import com.cristiancid.taskmanager.model.User;
import com.cristiancid.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return  userRepository.findById(id);
    }

    public boolean deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public Optional<User> updateUserById(Long id, UpdateUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()) {
            return Optional.empty();
        }
        User user = optionalUser.get();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        userRepository.save(user);
        return Optional.of(user);
    }
}
