package com.fira.app.apis;

import com.fira.app.requests.account.CreateAccountRequest;
import com.fira.app.requests.account.UpdateAccountRequest;
import com.fira.app.services.account.AccountService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/accounts")
public class AccountApi {

    private final AccountService accountService;



    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        try {
            return accountService.store(request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
