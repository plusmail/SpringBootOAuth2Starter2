package com.yi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages = {"com.yi"})
public class AppConfig {
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean // 비밀번호 암호화
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}