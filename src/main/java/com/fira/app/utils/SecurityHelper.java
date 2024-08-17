package com.fira.app.utils;

import com.fira.app.entities.Account;
import com.fira.app.repository.AccountRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityHelper {
    public static boolean isLogged() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public static void setAuthentication(UsernamePasswordAuthenticationToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public static String getLoggedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public static Account getAccountFromLogged(AccountRepository accountRepository) {
        String username = SecurityHelper.getLoggedUser();
        if (username == null) return null;
        Account account = accountRepository.findByUsername(username).orElse(null);
        if (account == null) return null;
        return account;
    }
}
