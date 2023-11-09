package com.controller;


import com.entity.Cart;
import com.entity.Order;
import com.model.CartRequest;
import com.response.SuccessResponse;
import com.service.CartService;
import com.service.ValidatorService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/carts")
public class CartController
{

    @Autowired
    private CartService cartService;

    @Autowired
    private ValidatorService validator;

    @GetMapping("/my-carts")
    public ResponseEntity<SuccessResponse<List<Cart>>> getAllCartsUser(@NotNull Long userId) {
        validator.validate(userId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<List<Cart>>builder()
                        .data(cartService.getAllCartByCurrentUser(userId))
                        .build()
        );
    }

    @PostMapping("/")
    public ResponseEntity<SuccessResponse<Cart>> addToCart(@RequestBody CartRequest req)  {
        validator.validate(req);
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<Cart>builder()
                        .data(cartService.store(req))
                        .build()
        );
    }

    @PostMapping("add-order")
    public ResponseEntity<SuccessResponse<Order>> addOrderFromCart(@Valid @NotNull Long cartId) {
        Long userId = 10L;// hard code
//        validator.validate(0);
        return  ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<Order>builder()
                        .data(cartService.createOrderFromCart(cartId,userId))
                        .build()
        );

    }

    public void delete(Long id) {
        cartService.delete(id);
    }
}
