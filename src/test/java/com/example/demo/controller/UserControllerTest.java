package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Mock
    private UserService userService; // Mock UserService

    @Mock
    private UserResponse userResponse; // Mock UserResponse

    @InjectMocks
    private UserController userController; // Inject Mocks เข้า UserController

    private List<User> mockUsers;
    private Long mockUserCount;

    @BeforeEach
    public void setUp() {
        // สร้างข้อมูล Mock
        mockUsers = Arrays.asList(new User(), new User(), new User());
        mockUserCount = 3L;
    }
    // assertEquals(expected, actual): เป็น method ใน JUnit ที่ใช้ตรวจสอบว่าค่า expected (ค่าที่คาดหวัง) เท่ากับค่า actual (ค่าที่ได้รับ) หรือไม่ ถ้าไม่เท่ากัน test จะ fail
    // assertNotNull(obj): เป็น method ใน JUnit ที่ใช้ตรวจสอบว่า obj (Object) ไม่เป็น null หรือไม่. ถ้าเป็น null test จะ fail.
    // verify(mockedObj): ใช้สำหรับตรวจสอบว่า interaction (การเรียกใช้งาน method) เกิดขึ้นตามที่คาดหวัง เช่น การเรียก method ของ mock object พร้อมพารามิเตอร์ที่ถูกต้อง
    // mockedObj มีการเรียกใช้งานเมธอดใดๆตามที่กำหนดไว้หรือไม่.

    @Test
    public void testGetAllMockUser() {
        ArrayList<Object> users = userController.getMockUsers();
        System.out.print("users:" + users);

        assertNotNull(users);
        assertEquals(2, users.size());

        // ตรวจสอบ User คนแรก
        User user1 = (User) users.get(0); // Cast to User
        assertEquals(1L, user1.getId());
        assertEquals("mockuser1", user1.getUsername());
        assertEquals("password1", user1.getPassword());

        // ตรวจสอบ User คนที่สอง
        User user2 = (User) users.get(1); // Cast to User
        assertEquals(2L, user2.getId());
        assertEquals("mockuser2", user2.getUsername());
        assertEquals("password2", user2.getPassword());
    }

    @Test
    public void testGetAllUsers() {
        // กำหนดพฤติกรรมของ Mock
        when(userService.getAllUsers()).thenReturn(mockUsers);
        when(userService.countAllUsers()).thenReturn(mockUserCount);

        // เรียก Method ที่ต้องการทดสอบ
        UserResponse result = userController.getAllUsers();

        // ตรวจสอบผลลัพธ์
        verify(userService).getAllUsers(); // ตรวจสอบว่า userService.getAllUsers() ถูกเรียก
        verify(userService).countAllUsers();// ตรวจสอบว่า userService.countAllUsers() ถูกเรียก

        verify(userResponse).setUsers(mockUsers); // ตรวจสอบว่า setUsers ถูกเรียกด้วย mockUsers
        verify(userResponse).setUserCount(mockUserCount); // ตรวจสอบว่า setUserCount ถูกเรียกด้วย mockUserCount

        assertEquals(userResponse, result); // ตรวจสอบว่า response ที่ได้ถูกต้อง (ถ้า equals method ของ UserResponse ถูก
                                            // implement) หรือใช้ assertNotNull(result) หากต้องการแค่ตรวจสอบว่าไม่เป็น
                                            // null
    }

    @Test
    public void testGetAllUsers_emptyList() {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        when(userService.countAllUsers()).thenReturn(0L);

        UserResponse result = userController.getAllUsers();

        verify(userService).getAllUsers();
        verify(userService).countAllUsers();

        verify(userResponse).setUsers(new ArrayList<>());
        verify(userResponse).setUserCount(0L);

        assertEquals(userResponse, result);
    }

    @Test
    public void testCreateUser() {
        // กำหนดพฤติกรรมของ Mock
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("newpassword");
        System.out.println("newUser: " + newUser);

        // กำหนดพฤติกรรมของ Mock (ใช้ doNothing())
        doNothing().when(userService).createUser(newUser.getUsername(), newUser.getPassword());

        // เรียก method ใน Controller โดยส่ง User object
        ResponseEntity<?> responseEntity = userController.createUser(newUser);
        System.out.println("responseEntity: " + responseEntity);

        // ตรวจสอบผลลัพธ์
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // ตรวจสอบ Status Code
        assertEquals(ResponseEntity.ok("User created successfully"), responseEntity); // ตรวจสอบ Body

        // เรียก Method ที่ต้องการทดสอบ
        verify(userService).createUser(newUser.getUsername(), newUser.getPassword()); // ตรวจสอบว่า
                                                                                      // userService.createUser()
                                                                                      // ถูกเรียก
    }

    @Test
    public void testDeleteUserById() {
        // สร้าง User object
        User userToDelete = new User();
        long userIdToDelete = 1L;
        userToDelete.setId(userIdToDelete);

        // กำหนดพฤติกรรมของ Mock
        doNothing().when(userService).deleteUserById(userIdToDelete);

        // เรียก method ใน Controller โดยส่ง User object
        ResponseEntity<?> responseEntity = userController.deleteUserById(userToDelete);

        // ตรวจสอบผลลัพธ์
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // ตรวจสอบ Status Code
        assertEquals(ResponseEntity.ok("User deleted successfully"), responseEntity); // ตรวจสอบ Body

        // ตรวจสอบว่า userService.deleteUserById() ถูกเรียกด้วย userIdToDelete
        verify(userService).deleteUserById(userIdToDelete);
    }

    @SuppressWarnings("null")
    @Test
    public void testUpdateUser() {
        // สร้าง User object
        User existingUser = new User();
        long userIdToUpdate = 1L;
        existingUser.setId(userIdToUpdate);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");

        User updatedUser = new User();
        updatedUser.setId(userIdToUpdate);
        updatedUser.setUsername("updatedUsername");
        updatedUser.setPassword("updatedPassword");

        List<User> users = new ArrayList<>();
        users.add(updatedUser);
        UserResponse expectedResponse = new UserResponse(users, 1L); // สร้าง UserResponse ที่คาดหวัง
        System.out.println("expectedResponse: " + expectedResponse.getUsers());

        // กำหนดพฤติกรรมของ Mock (แก้ไขตรงนี้)
        when(userService.findUserById(userIdToUpdate)).thenReturn(Optional.of(existingUser));
        doNothing().when(userService).saveUser(argThat(user -> user.getId().equals(userIdToUpdate)));

        // เรียก method ใน Controller โดยส่ง User object
        ResponseEntity<UserResponse> responseEntity = userController.editUserById(userIdToUpdate, updatedUser);
        System.out.println("responseEntity: " + responseEntity.getBody().getUsers());

        // ตรวจสอบผลลัพธ์
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // ตรวจสอบ Status Code
        assertNotNull(responseEntity.getBody()); // ตรวจสอบว่า body ไม่เป็น null
        assertEquals(expectedResponse.getUsers().get(0).getUsername(),
                responseEntity.getBody().getUsers().get(0).getUsername()); // ตรวจสอบ username ที่อัปเดตแล้ว
        assertEquals(expectedResponse.getUsers().get(0).getPassword(),
                responseEntity.getBody().getUsers().get(0).getPassword()); // ตรวจสอบ password ที่อัปเดตแล้ว
        assertEquals(expectedResponse.getUserCount(), responseEntity.getBody().getUserCount());

        // ตรวจสอบว่า userService.findUserById() และ userService.saveUser() ถูกเรียก
        verify(userService).findUserById(userIdToUpdate);
        verify(userService).saveUser(existingUser); // ใช้ existingUser ที่ถูกแก้ไขแล้ว
    }

}
