package com.app.umami.service;

import com.app.umami.entity.User;
import com.app.umami.exception.AlreadyExistException;
import com.app.umami.exception.ForbiddenException;
import com.app.umami.exception.InternalServerException;
import com.app.umami.exception.ResourceNotFoundException;
import com.app.umami.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User login(User user) {
        User oldUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Username or password is incorrect"));

        if (!passwordEncoder.matches(user.getPassword(), oldUser.getPassword())) {
            throw new ForbiddenException("Username or password is incorrect");
        }

        return oldUser;
    }

    public User signup(User user) {
        User newUser = null;
        Optional<User> oldUser = userRepository.findByEmail(user.getEmail());

        if (oldUser.isPresent()) {
            throw new AlreadyExistException("User already exists with email" + user.getEmail());
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser = userRepository.save(user);
        } catch (Exception e) {
            throw new InternalServerException("Error occurred during signup" + e);
        }

        return newUser;
    }

    public void logout() {

    }

    public List<User> getAllUsers() {
        List<User> users = null;

        try {
            users = userRepository.findAll();
        } catch (Exception e) {
            throw new InternalServerException("Error occurred while getting all users");
        }

        return users;
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found"));
    }

    public User updateUser(String id, User user) {
        User oldUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        oldUser.setFullName(user.getFullName());
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhone(user.getPhone());
        oldUser.setImgUrl(user.getImgUrl());

        return userRepository.save(oldUser);
    }

    public void deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No user found"));
        userRepository.delete(user);
    }

}
