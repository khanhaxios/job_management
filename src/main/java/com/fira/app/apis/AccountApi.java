package com.fira.app.apis;

import com.fira.app.requests.account.UpdateAccountRequest;
import com.fira.app.services.account.AccountService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountApi {

    private final AccountService accountService;

    @PutMapping("/compete-account/{id}")
    public ResponseEntity<?> completeAccount(@PathVariable(name = "id") String id, @Valid @RequestBody UpdateAccountRequest request) {
        try {
            return accountService.completeAccountInfo(id, request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
