package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest {

    @NotNull(message = "product id is required")
    @JsonProperty("product_id")
    private Long productId;

    // must get by token or from header
    @NotNull(message = "user id is required")
    @JsonProperty("current_user")
    private Long currentUser;

    @Min(value = 1, message = "Value must be at least 1")
    @Max(value = 10, message = "Value must be at most 10")
    @NotNull(message = "qty is required")
    private Integer qty;

}
