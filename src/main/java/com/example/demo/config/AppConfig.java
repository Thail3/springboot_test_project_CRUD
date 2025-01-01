package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.dto.ProductResponse;
import com.example.demo.dto.UserResponse;

@Configuration
public class AppConfig {
    @Bean
    public UserResponse userResponse() {
        return new UserResponse(null, null);  // สามารถกำหนดค่าเริ่มต้นได้
    }

    @Bean
    public ProductResponse productResponse() {
        return new ProductResponse(null, 0);  // สามารถกำหนดค่าเริ่มต้นได้
    }
}
