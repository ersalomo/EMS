package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerchantRequest {

    @Size(min = 4, max = 100)
    @NotBlank
    @JsonProperty("merchant_name")
    private String merchantName;

    @Size(min = 4, max = 100)
    @NotBlank
    @JsonProperty("merchant_location")
    private String merchantLocation;

}
