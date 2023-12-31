package com.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotFoundResponse {
    @Builder.Default
    private String status = "fail";

    @JsonProperty
    private String message;

    @JsonProperty
    @Builder.Default
    private int statusCode = HttpStatus.NOT_FOUND.value();
}
