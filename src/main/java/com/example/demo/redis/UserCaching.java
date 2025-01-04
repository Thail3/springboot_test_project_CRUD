package com.example.demo.redis;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;

// ส่วนโค้ด	หน้าที่
// @Aspect	บอกว่าเป็นคลาส Aspect สำหรับ AOP
// RedisTemplate<String, Object>	ใช้เชื่อมต่อและจัดการข้อมูลใน Redis
// @After	กำหนด Advice ที่ทำงานหลังจากเมธอดที่ระบุถูกเรียกใช้
// JoinPoint	ใช้ดึงข้อมูลเกี่ยวกับการเรียกใช้เมธอด เช่น อาร์กิวเมนต์
// redisTemplate.opsForValue().set()	เก็บข้อมูลแบบ key-value ลงใน Redis

// ประโยชน์ของการใช้ AOP กับ Redis Cache
// แยกความสนใจ (Separation of Concerns):
// Caching ถูกจัดการใน Aspect โดยไม่กระทบ business logic
// ลดภาระของฐานข้อมูล:
// การดึงข้อมูลที่เคยสร้างไว้แล้วสามารถทำได้จาก Redis
// Reusable:
// Aspect เดียวสามารถใช้กับหลายเมธอดหรือหลาย service ได้
// Maintenance ง่าย:
// สามารถเพิ่ม logic หรือแก้ไขการจัดการ Cache ได้จากที่เดียว

@Aspect // AOP จะทำการสแกนคลาสนี้ตรวจสอบว่ามี Advice ใดที่ต้องนำไปแทรกในระบบ (โมดูลที่เพิ่มการทำงานพิเศษ เช่น Logging หรือ Caching)
@Component
public class UserCaching {
    private final RedisTemplate<String, Object> redisTemplate;

    public UserCaching(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @SuppressWarnings("null")
    @AfterReturning( // จะถูกเรียกทำงาน และสามารถเข้าถึงค่าผลลัพธ์ที่คืนกลับมาจาก method createUser
        pointcut = "execution(* com.example.demo.service.UserService.createUser(..))",
        returning = "user" // รับค่าผลลัพธ์จาก method createUser
    )
    // @After("execution(* com.example.demo.service.UserService.createUser(String, String))") // execution() คือการระบุจุดที่จะแทรก Advice จะถูกเรียก หลังจาก method นั้นๆ ทำงานเสร็จแล้ว โดยไม่สนใจว่า method นั้นๆ จะคืนค่าหรือเกิด exception
    public void cacheCreateUser(JoinPoint joinPoint, User user) {
        System.out.println("cacheUser is called!");

        // logic do before createOrder
        Object[] args = joinPoint.getArgs(); // ดึงอาร์กิวเมนต์จาก JoinPoint
        System.out.println("Arguments: " + Arrays.toString(args));

        if (args.length < 2 && user == null && user.getId() == null) {
            System.err.println("User or ID is null, skipping cache.");
            return;
        }

        // เก็บข้อมูลผู้ใช้ลง Redis
        String cacheKey = "user:" + user.getId(); // ใช้ ID เป็น key

        redisTemplate.opsForValue().set(cacheKey, user); // ใช้ RedisTemplate เพื่อเขียนข้อมูลลงใน Redis
        // redisTemplate.opsForValue().get(cacheKey); // ใช้ RedisTemplate เพื่ออ่านข้อมูลจาก Redis
        
        // Logging
        System.out.println("Cached user with ID: " + user.getId());
        System.out.println(redisTemplate.opsForValue().get(cacheKey));
    }

    @AfterReturning(
        pointcut = "execution(* com.example.demo.service.UserService.editUser(..))", 
        returning = "userResponse")
    public void cacheEditUser(JoinPoint joinPoint, UserResponse userResponse) {
        // ดึงค่าอาร์กิวเมนต์จาก JoinPoint
        Object[] args = joinPoint.getArgs();
        long id = (long) args[0]; // ดึง id จากอาร์กิวเมนต์ตัวแรก
        User user = ((UserResponse) userResponse).getUsers().get(0); // ดึง User จากผลลัพธ์
        System.out.println("cacheEditUser is called! - id: " + id + "user: " + user);

        if (id <= 0 || user == null) { // ตรวจสอบว่าไอดีผู้ใช้หรือ User เป็น null
            System.err.println("Invalid arguments for editUser");
            return;
        }

         // เก็บข้อมูลผู้ใช้ลง Redis
        String cacheKey = "user:" + id;
        redisTemplate.opsForValue().set(cacheKey, user);
    }

    @After("execution(* com.example.demo.service.UserService.deleteUserById(Long))")
    public void deleteCacheUser(JoinPoint joinPoint) {
        System.out.println("deleteCacheUser is called!");

        Long userId = (Long) joinPoint.getArgs()[0]; // ดึงไอดีผู้ใช้จากอาร์กิวเมนต์
        String cacheKey = "user:" + userId; // ใช้ไอดีผู้ใช้เป็น key ใน Redis

        redisTemplate.delete(cacheKey); // ลบข้อมูลผู้ใช้ออกจาก Redis
    }
}
