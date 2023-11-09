package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MerchantRequest {

    @Size(min = 4, max = 100)
    @NotBlank(message = "merchant name is required")
    @JsonProperty("merchant_name")
    private String merchantName;

    @Size(min = 4, max = 100)
    @NotBlank(message = "merchant location is required")
    @JsonProperty("merchant_location")
    private String merchantLocation;

}
