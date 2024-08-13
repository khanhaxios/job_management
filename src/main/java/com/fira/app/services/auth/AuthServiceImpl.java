package com.fira.app.services.auth;

import com.fira.app.entities.Account;
import com.fira.app.expceptions.AppException;
import com.fira.app.repository.AccountRepository;
import com.fira.app.requests.auth.LoginByCard;
import com.fira.app.requests.auth.LoginRequest;
import com.fira.app.responses.auth.AccountResponse;
import com.fira.app.services.jwt.JwtService;
import com.fira.app.utils.BeanHelper;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountRepository accountRepository;

    private final JwtService jwtService;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Account account = accountRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (account == null) {
            return ResponseHelper.badRequest("Account not found");
        }
        if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), account.getPassword())) {
            return ResponseHelper.badRequest("Password not match");
        }
        if (!account.isActive()) {
            return ResponseHelper.badRequest("Your account is baned");
        }
        return mapAccountResponse(account);
    }

    @Override
    public ResponseEntity<?> loginWithIdCard(LoginByCard loginRequest) {
        Account account = accountRepository.findByUsername(loginRequest.getUsername()).orElse(null);
        if (account == null) {
            return ResponseHelper.badRequest("Account not found");
        }
        if (!account.getIdCard().getID().equals(loginRequest.getCardId())) {
            throw new AppException("Card ID not match");
        }
        if (!account.getIdCard().getPin().equals(loginRequest.getPin())) {
            throw new AppException("Pin card not match");
        }
        return mapAccountResponse(account);
    }

    private ResponseEntity<?> mapAccountResponse(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        BeanUtils.copyProperties(accountResponse, accountResponse, BeanHelper.getNullPropertyNames(account));
        accountResponse.setRole(account.getRole());
        String token = jwtService.signToken(account);
        accountResponse.setToken(token);
        return ResponseHelper.success(accountResponse);
    }
}
