package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUpdateReq {

    @Size(min = 4, max = 100)
    @JsonProperty("product_name")
    @NotBlank(message = "product name is required")
    private String productName;

    @DecimalMin(value = "1.0", message = "Price value is required")
    private double price;
}
