package com.example.transfolio.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static String getLoginIdFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = null;

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                loginId = (String) principal;
            }else {
                System.out.println("Principal type: " + principal.getClass().getName());
            }
        } else {
            throw new IllegalStateException("Unexpected authentication principal type: " + authentication.getPrincipal().getClass());
        }

        return loginId;
    }
}
