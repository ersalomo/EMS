package com.controller;


import com.entity.Product;
import com.model.ProductRequest;
import com.model.ProductUpdateReq;
import com.response.SuccessResponse;
import com.service.ProductService;
import com.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ValidatorService validator;


    @PostMapping
    public ResponseEntity<SuccessResponse<Product>> create(@RequestBody ProductRequest request) {

        validator.validate(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<Product>builder()
                        .statusCode(HttpStatus.CREATED)
                        .message("Product added")
                        .data(productService.create(request))
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<Product>>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<List<Product>>builder()
                        .data(productService.get())
                        .build()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<Product>> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<Product>builder()
                        .data(productService.find(id))
                        .build()
        );
    }

    @PatchMapping("{id}")
    public ResponseEntity<SuccessResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateReq req) {
        validator.validate(req);

        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<Product>builder()
                        .data(productService.update(id, req))
                        .message("Product updated")
                        .build()
        );
    }


    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<String>> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<String>builder()
                        .message("Product deleted")
                        .build()
        );
    }
}
