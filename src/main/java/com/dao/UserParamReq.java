package com.dao;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserParamReq {
    private String username;
    private String email;
    private int page;
    private int size;
}
