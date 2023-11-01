package com.controller;


import com.dao.MerchantParamReq;
import com.entity.Merchant;
import com.model.MerchantRequest;
import com.model.Response;
import com.model.StatusRequest;
import com.service.MerchantService;
import com.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("api/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> store(@Valid @RequestBody MerchantRequest req) {
        merchantService.add(req);
        return ResponseEntity.ok("Merchant added");
    }
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateStatusOpen(
            @PathVariable Long id,
            @Valid @RequestBody StatusRequest status) {
        log.info("Status " + status.getStatus()  + " pusing " );
        merchantService.updateStatus(id, Util.trueOrFalse(status.getStatus()));
        Map<String, String> res = new HashMap<>();
        res.put("status",  "success");
        res.put("message",  "Merchant status updated successfully");
        return ResponseEntity.ok(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {

        return ResponseEntity.ok(merchantService.findById(id));
    }


    @GetMapping
    public ResponseEntity<Map<String,?>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false, defaultValue = "3" ) int open,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        MerchantParamReq req = new MerchantParamReq();
        req.setName(name);
        req.setLocation(location);
        req.setOpen(open);
        req.setSize(size);
        req.setPage(page);
        Map<String, Object> res = new HashMap<>();
        List<Merchant> merchants;
        log.info(req + " Here " + open);
        if (open == 3 && location == null && name == null) {
            Page<Merchant> merchantPage = merchantService.findAll(PageRequest.of(page, size));
            merchants = merchantPage.getContent();
        }else{
            merchants  = merchantService.findAll(req);
        }

        if (merchants.isEmpty()) {
            res.put("status",  "fail");
            res.put("message",  "Not Found" );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }

        res.put("status", "success");
        res.put("data", merchants);

        return ResponseEntity.status(HttpStatus.OK).body(res);

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


    @GetMapping("/report")
    public ResponseEntity<?> getReport(

    ) {
        merchantService.generateReportingMerchant();
        return ResponseEntity.ok("This your report");
    }
}
