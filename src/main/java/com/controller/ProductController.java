package com.controller;


import com.model.ProductRequest;
import com.model.Response;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody ProductRequest request) {
        productService.create(request);
        return ResponseEntity.status(HttpStatus.OK).body("Product added");
    }

    @GetMapping
    public ResponseEntity<Map<String, ?>> findAll() {
        Map<String, Object> res = new HashMap<>();
        res.put("status", "");
        res.put("data", productService.get());
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

}
