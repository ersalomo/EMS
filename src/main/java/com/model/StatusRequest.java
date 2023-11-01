package com.model;


import lombok.*;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatusRequest {

@NotNull
    private String status;
}
