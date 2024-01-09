package com.example.memorizing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MemorizingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemorizingApplication.class, args);
    }
}
