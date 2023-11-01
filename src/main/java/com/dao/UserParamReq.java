package com.dao;

import lombok.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserParamReq extends ParamReqPagination {
    private String username;
    private String email;

}
