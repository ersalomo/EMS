package com.response;


import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessResponse {
    private String message;
}
