package com.fira.app.apis;

import com.fira.app.requests.account.CreateAccountRequest;
import com.fira.app.services.account.AccountService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam(name = "sortBy") String sortBy, @RequestParam(name = "sortDirection") String sortDir, @RequestParam(name = "query") String query) {
        try {
            return accountService.getAll(pageable, sortBy, sortDir, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
