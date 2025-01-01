//package com.example.demo.security;
//package security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.DefaultSecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class scurityConfig {
//    @Bean
//    public DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable) // ปิด CSRF
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // อนุญาตทุก request
//        return http.build();
//    }
//}
