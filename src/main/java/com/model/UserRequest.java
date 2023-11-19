package com.model;


import com.Enum.RoleUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @Email
    @Size(min = 8, max = 100)
    @NotBlank
    private String email;

    @Size(min = 8, max = 100)
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 255)
    private String password;


    @Valid
    @NotNull
    private RoleUser roleUser;

}
