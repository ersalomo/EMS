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
    @Size(min = 6)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

}
