package com.example.demo.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.entity.User;

import lombok.Getter;
import lombok.Setter;

//! DTO (Data Transfer Object) (UserResponse): ใช้สำหรับส่งข้อมูลไปยัง client ในรูปแบบที่เหมาะสมกับ API

@Getter
@Setter
@Component
public class UserResponse {
    // Instance variables
    private List<User> users;   // รายชื่อผู้ใช้
    private Long userCount;     // จำนวนผู้ใช้ทั้งหมด

    // Constructor
    public UserResponse(List<User> users, Long userCount) {
        this.users = users;
        this.userCount = userCount;
    }

    // Factory method สำหรับสร้าง UserResponse
    // public static UserResponse from(List<User> users, Long userCount) {
    //     return new UserResponse(users, userCount);
    // }

    // Example JSON response:
    // {
    //     "users": [
    //         {
    //             "id": 1,
    //             "username": "hello",
    //             "password": "world"
    //         },
    //     ],
    //     "userCount": 1
    // }
}
