package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.crypto.Mac;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {

    @Size(min = 4, max = 100)
    @JsonProperty("product_name")
    @NotBlank(message = "product name is required")
    private String productName;

    @DecimalMin(value = "1.0", message = "Price value is required")
    private double price;

    @JsonProperty(value = "merchant_id")
    @NotNull(message = "merchantId is required")
    private Long merchantId;
}
