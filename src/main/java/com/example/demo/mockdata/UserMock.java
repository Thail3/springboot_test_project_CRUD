package com.example.demo.mockdata;

import java.util.ArrayList;

import com.example.demo.entity.User;


public class UserMock {
    public ArrayList<Object> getUserName() {
        ArrayList<Object> mockUsers = new ArrayList<>();

        User user1 = new User( );
        user1.setId(1L);
       user1.setUsername("mockuser1");
       user1.setPassword("password1");

        User user2 = new User();
        user2.setId(2L);
       user2.setUsername("mockuser2");
       user2.setPassword("password2");

        // Log ข้อมูล
        System.out.println("Mock User1: " + user1);
        System.out.println("Mock User2: " + user2);

//  *   ตัวอย่างการเพิ่มข้อมูลลงใน ArrayList<Object>
//  *      [
//  *          {
//  *              "id": 1,
//  *              "username": "mockuser1",
//  *              "password": "password1"
//  *          },
//  *          {
//  *              "id": 2,
//  *              "username": "mockuser2",
//  *              "password": "password2"
//  *          }
//  *      ]

        mockUsers.add(user1);
        mockUsers.add(user2);

        return mockUsers;
    }
}