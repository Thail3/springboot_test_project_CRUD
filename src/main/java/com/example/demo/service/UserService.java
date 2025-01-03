package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService implements IUserService {
    @Autowired // ใช้ @Autowired เพื่อให้ Spring Boot inject repository ให้
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findUserById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Long countAllUsers() {
        return userRepository.count();
    }

    @Override
    public void createUser(String username, String password) {
        // สร้าง User entity จากข้อมูลที่รับมา
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        // บันทึก User entity ลงในฐานข้อมูล
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
    
}
