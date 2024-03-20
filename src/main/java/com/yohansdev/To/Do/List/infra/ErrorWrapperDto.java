package com.yohansdev.To.Do.List.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ErrorWrapperDto {
    private HttpStatus statusCode;
    private String message;
    private Map<String, String> validationErrors;

    ErrorWrapperDto(HttpStatus statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }

    ErrorWrapperDto(HttpStatus statusCode, Map<String, String> errorsMap){
        this.statusCode = statusCode;
        this.validationErrors = errorsMap;
    }

    ErrorWrapperDto(Map<String, String> errorsMap){
        this.validationErrors = errorsMap;
    }
}
