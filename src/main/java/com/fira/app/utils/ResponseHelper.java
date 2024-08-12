package com.fira.app.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class ResponseHelper {
    public static int VALID_ERROR_CODE = 1001;
    public static int NOT_FOUND_CODE = 1002;

    public static ResponseEntity<?> invalid(Map<String, String> msgs) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().messages(msgs).code(VALID_ERROR_CODE).build(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().message(message).code(NOT_FOUND_CODE).build(), HttpStatus.NOT_FOUND);
    }
}
