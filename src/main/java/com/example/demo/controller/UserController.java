package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.mockdata.UserMock;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping() // ? Annotate controller class with base URL prefix
public class UserController {
    // * Instance variables
    private final UserService userService; // ไม่ต้อง new UserService เนื่องจาก Spring จะสแกนคลาสที่มี annotation
    private final UserResponse userResponse;
    private final UserMock userMock = new UserMock();

    // * Constructor with dependency injection
    @Autowired // * Inject UserService instance into constructor parameter (ช่วยให้ Spring
               // รู้ว่าจะ Inject Bean อะไร)
    public UserController(UserService userService, UserResponse userResponse) {
        this.userService = userService;
        this.userResponse = userResponse;
    }

    // ? Mock endpoint เพื่อทดสอบการเรียกใช้งาน API
    @GetMapping("/mock-all-users") // * Annotate method as a GET request handler at "/mock-all-users" endpoint
    // ? Method returns an ArrayList containing mock data
    public ArrayList<Object> getMockUsers() {
        System.out.println("Endpoint /mock-all-users called");
        return userMock.getUserName();
    }

    @GetMapping("/users") // * Annotate method as a GET request handler at "/users" endpoint
    // * Method to retrieve all users
    // ? Return type is List<User>, which represents the collection of User objects
    public UserResponse getAllUsers() {
        System.out.println("Endpoint /users");
        List<User> users = userService.getAllUsers();
        System.out.println("Number of users: " + users);
        Long userCount = userService.countAllUsers();
        System.out.println("User count: " + userCount);

        // UserResponse userRes = new UserResponse(users, userCount); //* สร้าง object
        // UserResponse จาก list user และจำนวน user ในกรณีไม่ได้ประกาศ Instance variable
        // return userRes;

        // ใช้ constructor ของ userResponse ที่ถูก inject มา
        userResponse.setUsers(users);
        userResponse.setUserCount(userCount);
        return userResponse;
    }

    // Example usage:
    // http://localhost:8080/api/user/1
    @GetMapping("/user/{id}")
    public UserResponse getUserById(@PathVariable("id") long id) {
        // Optional ใช้สำหรับการจัดการค่าที่อาจเป็น null เพื่อหลีกเลี่ยงการเกิด
        // NullPointerException
        Optional<User> user = userService.findUserById(id);
        System.out.println("Endpoint /user/" + id);

        if (user.isPresent()) {
            // ถ้าพบผู้ใช้ ให้ return UserResponse ที่มี users เป็น list ของ user ที่พบ
            List<User> users = new ArrayList<>();
            users.add(user.get()); // เพิ่ม user ที่ค้นหาได้เข้าไปใน list
            return new UserResponse(users, (long) users.size()); // ส่งกลับ UserResponse
        } else {
            return new UserResponse(new ArrayList<>(), 0L); // ถ้าไม่พบ user, ส่งกลับ list ว่างและ userCount เป็น 0
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
        System.out.println("Endpoint /user/create");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Password: " + user.getPassword());
        userService.createUser(user.getUsername(), user.getPassword());
        return ResponseEntity.ok("User created successfully");
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUserById(@Valid @RequestBody User user) {
        System.out.println("Endpoint /user/delete");
        System.out.println("User ID: " + user.getId());
        userService.deleteUserById(user.getId());
        return ResponseEntity.ok("User deleted successfully");
    }

    @PutMapping("edit-user/{id}")
    public ResponseEntity<?> editUserById(@PathVariable long id, @RequestBody User user) {
        userService.editUser(id, user);

        return ResponseEntity.ok("User edited successfully");
    }
}
