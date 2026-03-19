package com.caleb.taskflow.exception;

import com.caleb.taskflow.dto.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex)  {
        ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return  new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // not found exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return  new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // handle validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiError apiError = new ApiError(400, "Validation failed: " + errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // handle access denied exceptions
    @ExceptionHandler(AccessDeniedException.class)
    public  ResponseEntity<ApiError> handleAccessDeniedExceptions(AccessDeniedException ex) {
        ApiError error = new ApiError(HttpStatus.FORBIDDEN.value(), ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    // handle already exists exceptions
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAlreadyExistsException(AlreadyExistsException ex) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());

        return  new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
