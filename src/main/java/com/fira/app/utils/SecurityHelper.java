package com.fira.app.utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityHelper {
    public static boolean isLogged() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    public static void setAuthentication(UsernamePasswordAuthenticationToken token) {
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
