package com.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;
    public void generateInvoice() {

    }
}
