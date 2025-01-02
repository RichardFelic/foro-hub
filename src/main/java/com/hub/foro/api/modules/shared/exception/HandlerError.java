package com.hub.foro.api.modules.shared.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hub.foro.api.modules.shared.validation.ValidationException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class HandlerError {
    // Handle errors 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> hadleError404() {
        return ResponseEntity.notFound().build();
    }

    // Handle errors 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> hadleError400(MethodArgumentNotValidException e) {
        var errors = e.getFieldErrors().stream().map(HandleValidationError::new).toList();
        return ResponseEntity.badRequest().body(errors);
    }

    private record HandleValidationError(String field, String message) {
        public HandleValidationError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    // Handle errors 403
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> hadleError403(ValidationException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    // handleIllegalArgumentException for bad request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "message", ex.getMessage(),
                "path", ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString()));
    }

}
