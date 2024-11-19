package com.cheolhyeon.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Builder
@Getter
public class ErrorResponse {
    private String message;
    private HttpStatus status;
}
