package com.fira.app.apis;

import com.fira.app.requests.account.CreateAccountRequest;
import com.fira.app.services.account.AccountService;
import com.fira.app.utils.ResponseHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/accounts")
public class AccountApi {
    private final AccountService accountService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        try {
            return accountService.store(request);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @GetMapping
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam(name = "sortBy", defaultValue = "username") String sortBy, @RequestParam(name = "sortDirection", defaultValue = "desc") String sortDir, @RequestParam(name = "query", defaultValue = "") String query) {
        try {
            return accountService.getAll(pageable, sortBy, sortDir, query);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
