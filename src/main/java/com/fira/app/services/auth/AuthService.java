package com.fira.app.services.auth;

import com.fira.app.requests.auth.LoginByCard;
import com.fira.app.requests.auth.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> loginWithIdCard(LoginByCard loginRequest);

}
