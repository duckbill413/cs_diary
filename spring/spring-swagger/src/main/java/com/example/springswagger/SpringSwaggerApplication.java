package com.example.springswagger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class SpringSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSwaggerApplication.class, args);

        log.info("hi swagger");
    }

}
