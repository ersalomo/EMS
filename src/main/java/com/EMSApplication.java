package com;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EMSApplication {

    public static void main(String[] args) {
        SpringApplication.run(EMSApplication.class, args);
        log.info("Server is running on localhost:8001");
    }

}