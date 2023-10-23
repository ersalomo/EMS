package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {


    @NotBlank(message = "product id is required")
    @Value("product_id")
    private Long productId;

    @NotBlank(message = "product id is required")
    @Min(1)
    @Max(10)
    private Long qty;

}
