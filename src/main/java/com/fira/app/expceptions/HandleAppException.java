package com.fira.app.expceptions;

import com.fira.app.utils.ApiResponse;
import com.fira.app.utils.ResponseHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleAppException {

    @ExceptionHandler(AppException.class)
    public void handleAppException(AppException exception) {

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidRequestException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String errorMessage = error.getDefaultMessage();
            String columnError = ((FieldError) error).getField();
            errors.put(columnError, errorMessage);
        }
        return ResponseHelper.invalid(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
