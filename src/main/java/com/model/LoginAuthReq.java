package com.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAuthReq {
//    @Size(min = 8, max = 100)
//    private String username;
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;
}
