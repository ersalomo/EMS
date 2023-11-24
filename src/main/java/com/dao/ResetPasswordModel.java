package com.dao;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordModel {
    @NotBlank
    private String otp;
    @NotBlank
    private String email;
    @NotBlank
    private String newPassword;
}
