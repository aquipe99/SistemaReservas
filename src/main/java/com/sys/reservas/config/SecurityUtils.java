package com.sys.reservas.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {}
    public static Integer getCurrentUserId() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }

        return ((CustomUserDetails) auth.getPrincipal()).getId();
    }
}
