package com.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SuccessResponse<T> {
    @Builder.Default
    private String status = "success";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HttpStatus statusCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

}
