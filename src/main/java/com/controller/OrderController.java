package com.controller;


import com.dao.OrderRequest;
import com.entity.Order;
import com.entity.User;
import com.response.SuccessResponse;
import com.service.InvoiceService;
import com.service.OrderService;
import com.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ValidatorService validator;


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<Order>> create(
           @RequestBody OrderRequest request
    ) {
        validator.validate(request);
        Order order = orderService.addNewOrder(request);
        order.getUser().setPassword(null);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<Order>builder()
                        .message("Order placed")
                        .data(order)
                        .build()
        );
    }


    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<Order>> show(@PathVariable Long id) {
        Order order = orderService.findDetailOrder(id);
        return ResponseEntity.ok(
                SuccessResponse.<Order>builder()
                        .message("Your Order")
                        .data(order)
                        .build()
        );
    }


    @PostMapping("/order-invoice")
    public ResponseEntity<SuccessResponse<String>> getInvoice() {
        invoiceService.generateInvoice();
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<String>builder()
                        .message("Order")
                        .build()
        );
    }
}
