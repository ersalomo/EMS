package com.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private String message;
    @Builder.Default
    private String status = "success";
    @Builder.Default
    private HttpStatus code = HttpStatus.OK;
    private T data;

}
