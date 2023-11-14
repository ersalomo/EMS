package com;


import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class EMSApplication {

    @org.springframework.beans.factory.annotation.Value("${server.port}")
    private Integer port;

    private Integer getPort() {
        return this.port;
    }
    public static void main(String[] args) {
        EMSApplication emsApplication = SpringApplication.run(EMSApplication.class, args).getBean(EMSApplication.class);
        log.info("Server is running on http:\\\\localhost:{}", emsApplication.getPort());
    }

}