package com.dao;


import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MerchantParamReq extends ParamReqPagination {
    private String name;
    private String location;
    private String open;
}
