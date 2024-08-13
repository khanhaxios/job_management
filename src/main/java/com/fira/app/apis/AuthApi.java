package com.fira.app.apis;

import com.fira.app.requests.auth.LoginByCard;
import com.fira.app.requests.auth.LoginRequest;
import com.fira.app.services.auth.AuthService;
import com.fira.app.utils.ApiResponse;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public/auth")
@Slf4j
public class AuthApi {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            return authService.login(loginRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PostMapping("/with-id-card")
    public ResponseEntity<?> loginWithIdCard(@RequestBody @Valid LoginByCard loginRequest) {
        try {
            return authService.loginWithIdCard(loginRequest);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
