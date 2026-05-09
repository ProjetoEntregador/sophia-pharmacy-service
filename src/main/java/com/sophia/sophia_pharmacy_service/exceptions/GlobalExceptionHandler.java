package com.sophia.sophia_pharmacy_service.exceptions;

import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ApiResponseDto<Void> response = new ApiResponseDto<>("error: DataIntegrityViolation", null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleRuntime(RuntimeException ex) {
        ApiResponseDto<Void> response = new ApiResponseDto<>("error: RunTime",null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleInvalidJwt(InvalidJwtException ex) {
        ApiResponseDto<Void> response = new ApiResponseDto<>("error: InvalidJwt",null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleAccessDenied(AccessDeniedException ex) {
        ApiResponseDto<Void> response = new ApiResponseDto<>("error: AccessDenied",null, ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
