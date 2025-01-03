package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.User;

public interface IUserService {
    List<User> getAllUsers();
    
    Optional<User> findUserById(long id);

    void saveUser(User user);

    Long countAllUsers();

    void createUser(String username, String password);

    void deleteUserById(Long userId);
}
