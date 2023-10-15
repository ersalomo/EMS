package com.controller;


import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    @RequestMapping("/")
    public String hello() {
        return "Hello every body";
    }

    @PostMapping("/Hello")
    public int test() {
        return 10;
    }
    @GetMapping("/Hello")
    public int testMe() {
        return 2033434;
    }
    @GetMapping ("/Hello-1")
    public int testMeD() {
        return 2033434;
    }

}
