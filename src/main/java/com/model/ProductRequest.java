package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {

    @Size(min = 4, max = 100)
    @Max(30)
    @JsonProperty("product_name")
    private String productName;

    @Min(0)
    private double price;

    @NotBlank
    @JsonProperty("merchant_id")
    private Long merchantId;
}
