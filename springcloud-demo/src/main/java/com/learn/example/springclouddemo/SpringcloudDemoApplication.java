package com.learn.example.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.learn.redis"})
@SpringBootApplication
public class SpringcloudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringcloudDemoApplication.class, args);
    }

}
