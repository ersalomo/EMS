package com.controller;


import com.model.ProductRequest;
import com.model.ProductUpdateReq;
import com.model.Response;
import com.response.SuccessResponse;
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

    @PatchMapping("{id}")
    public ResponseEntity<SuccessResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductUpdateReq req
            ) {
        productService.update(id, req);
        return ResponseEntity.ok(SuccessResponse.builder().message("Product updated").build());
    }


    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok(SuccessResponse.builder().message("Product deleted").build());
    }
}
