package com.controller;


import com.entity.Merchant;
import com.model.MerchantRequest;
import com.model.Response;
import com.model.StatusRequest;
import com.service.MerchantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping(
            path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> store(@Valid @RequestBody MerchantRequest req) {
        merchantService.add(req);
        return ResponseEntity.ok("Merchant added");
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateStatusOpen(@PathVariable Long id, @Valid @RequestBody StatusRequest status) {
        merchantService.updateStatus(id, status.isStatus());
        Map<String, String> res = new HashMap<>();
        res.put("status",  "success");
        res.put("message",  "Merchant status updated");
        return ResponseEntity.ok(res);
    }


    @GetMapping()
    public ResponseEntity<Map<String,?>> findAll() {
        List<Merchant> merchants  = merchantService.findAll();
        Map<String, Object> res = new HashMap<>();
        if (merchants.isEmpty()) {
            res.put("status",  "fail");
            res.put("message",  "Not Found" );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        res.put("status", "success");
        res.put("data", merchants);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Map<String, Object> res = new HashMap<>();
        log.info(String.format("[delete] data from merchant with id %s", id));
        merchantService.delete(id);
        res.put("status",  "fail");
        res.put("message",  "Data Deleted" );
        return ResponseEntity.ok("");
    }
}
