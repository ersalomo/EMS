package com.controller;


import com.dao.OrderRequest;
import com.entity.Order;
import com.entity.User;
import com.response.SuccessResponse;
import com.service.InvoiceService;
import com.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private InvoiceService invoiceService;


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(
           @Valid @RequestBody OrderRequest request
    ) {
        Order order = orderService.addNewOrder(request);
        User user = order.getUser();
        user.setPassword(null);
        order.setUser(user);
        return ResponseEntity.ok(SuccessResponse.builder().message("Order placed").data(order).build());
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
    public ResponseEntity<?> getInvoice() {
        invoiceService.generateInvoice();
        return ResponseEntity.ok("Wow");
    }
}
