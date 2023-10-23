package com.controller;


import com.model.CartRequest;
import com.response.SuccessResponse;
import com.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/carts")
public class CartController
{

    @Autowired
    private CartService cartService;

    @PostMapping("/")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest req)  {
        cartService.store(req);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Cart Added");
    }

    public void delete(Long id) {
        cartService.delete(id);
    }
}
