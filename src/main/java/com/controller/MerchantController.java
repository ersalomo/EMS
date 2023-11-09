package com.controller;


import com.dao.MerchantParamReq;
import com.entity.Merchant;
import com.model.MerchantRequest;
import com.model.StatusRequest;
import com.response.SuccessResponse;
import com.service.MerchantService;
import com.service.ValidatorService;
import com.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/merchants")
public class MerchantController {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private ValidatorService validator;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<Merchant>> store(@RequestBody MerchantRequest req) {
        validator.validate(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                SuccessResponse.<Merchant>builder()
                        .message("Merchant data created")
                        .statusCode(HttpStatus.CREATED)
                        .data(merchantService.add(req))
                        .build()
        );
    }
    @PutMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<Merchant>> updateStatusOpen(@PathVariable Long id, @Valid @RequestBody StatusRequest status) {
        log.info("Status {}", status.getStatus());
        Merchant merchant = merchantService.updateStatus(id, Util.trueOrFalse(status.getStatus()));
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<Merchant>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Merchant status updated successfully")
                        .data(merchant)
                        .build()
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<Merchant>> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<Merchant>builder()
                        .data(merchantService.findById(id))
                        .build()
        );
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
    public ResponseEntity<SuccessResponse<String>> delete(@PathVariable Long id) {
        merchantService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                SuccessResponse.<String>builder()
                        .message("Merchant deleted")
                        .build()
        );
    }


    @GetMapping("/report")
    public ResponseEntity<?> getReport() {
        merchantService.generateReportingMerchant();
        return ResponseEntity.ok("This your report");
    }
}
