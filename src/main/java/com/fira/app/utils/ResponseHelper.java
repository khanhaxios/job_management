package com.fira.app.utils;

import com.fira.app.entities.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public class ResponseHelper {
    public static int VALID_ERROR_CODE = 1001;
    public static int NOT_FOUND_CODE = 1002;
    public static int SUCCESS_CODE = 9999;
    public static int SERVER_ERROR = 1005;
    public static int BAD_REQUEST = 1004;
    public static int ACCESS_DENIED = 1009;
    public static int MISSING_PARAMS = 1122;

    public static ResponseEntity<?> invalid(Map<String, String> msgs) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().messages(msgs).code(VALID_ERROR_CODE).build(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> serverError(String message) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().message(message).code(SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<?> notFound(String message) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().message(message).code(NOT_FOUND_CODE).build(), HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<?> success(Object data) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().result(data).code(SUCCESS_CODE).build(), HttpStatus.OK);
    }

    public static ResponseEntity<?> success() {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().code(SUCCESS_CODE).build(), HttpStatus.OK);
    }

    public static ResponseEntity<?> badRequest(String message) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().code(BAD_REQUEST).message(message).build(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> accessDenied(String message) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().code(ACCESS_DENIED).message(message).build(), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> missingParams(String message) {
        return new ResponseEntity<>(new ApiResponse.ApiResponseBuilder().code(MISSING_PARAMS).message(message).build(), HttpStatus.BAD_REQUEST);
    }
}
