package com.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response <T>{

    private T data;

    private String errors;
}
