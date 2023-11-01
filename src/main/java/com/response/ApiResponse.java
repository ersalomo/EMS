package com.response;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponse extends RuntimeException {
    private String message;
}
