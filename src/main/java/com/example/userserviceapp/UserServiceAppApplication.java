package com.example.userserviceapp;

import com.example.userserviceapp.controllers.AuthController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class UserServiceAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceAppApplication.class, args);
    }

}
