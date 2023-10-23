package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class UserRequest {

    @Email
    @Size(min = 8, max = 100)
    private String email;

    @Size(min = 8, max = 100)
    private String username;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

}
