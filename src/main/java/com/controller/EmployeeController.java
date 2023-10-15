package com.model;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class EmployeeController {
    @RequestMapping("/")
    public String hello() {
        return "Hello every body";
    }
}
