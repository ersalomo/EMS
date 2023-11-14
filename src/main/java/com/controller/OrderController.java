package com.controller;


import com.dao.OrderRequest;
import com.entity.Order;
import com.entity.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.response.SuccessResponse;
import com.service.InvoiceService;
import com.service.OrderService;
import com.service.ValidatorService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

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


    @PostMapping("/get-invoice")
    public ResponseEntity<byte[]> getInvoice(
            @Valid @RequestParam @NotNull(message = "current user is required") Long userId ) throws IOException {
        String invFileName = invoiceService.generateInvoice(userId);
        File file  = new File(invFileName);
        byte[] fileContent = FileCopyUtils.copyToByteArray(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", invFileName);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }
}
