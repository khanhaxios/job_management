package com.fira.app.expceptions;

import com.fira.app.utils.ApiResponse;
import com.fira.app.utils.ResponseHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class HandleAppException {

//    @ExceptionHandler(AppException.class)
//    public ResponseEntity<?> handleAppException(AppException exception) {
//        return ResponseHelper.badRequest(exception.getMessage());
//    }

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

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeined(AccessDeniedException e) {
        return ResponseHelper.accessDenied(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> tokenInvalid(JwtException e) {
        return ResponseHelper.accessDenied(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParamException(MissingServletRequestParameterException e) {
        return ResponseHelper.missingParams(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherException(Exception exception) {
        return ResponseEntity.internalServerError().body(ApiResponse.builder().code(-11111).message(exception.getMessage()).build());
    }
}
