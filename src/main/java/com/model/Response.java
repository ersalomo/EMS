package com.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Response <T>{
    private T data;
    private String errors;
}
