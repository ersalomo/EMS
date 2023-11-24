package com.dao;


import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailReq {

    @NotBlank
    @Email
    private String email;
}
