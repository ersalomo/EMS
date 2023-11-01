package com.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequest {

    @Size(min = 4, max = 255)
    @JsonProperty("address")
    @NotNull(message = "address is required")
    private String destinationAddr;

    // it can be from header btw
    @JsonProperty("user_id")
    @NotNull(message = "user id is required")
    private Long userId;

    @JsonProperty("product_id")
    @NotNull(message = "product id is required")
    private Long productId;

    @Min(1)
    @Max(10)
    @JsonProperty("qty")
    @NotNull(message = "qty is required")
    private int qty;

}
