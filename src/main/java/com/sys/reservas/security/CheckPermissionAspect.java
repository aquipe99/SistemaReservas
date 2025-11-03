package com.sys.reservas.security;

import com.sys.reservas.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class CheckPermissionAspect {
    private final AuthorizationService authorizationService;

    @Before("@annotation(checkPermission)")
    public void checkAccess(JoinPoint joinPoint, CheckPermission checkPermission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new SecurityException("Unauthorized");
        }

        // ✅ Obtenemos el rol (por ejemplo: ROLE_ADMIN -> ADMIN)
        String roleName = auth.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");

        // ✅ Normalizamos el menú (por si viene con /api)
        String requestedMenu = checkPermission.menu().trim();

        System.out.println("🔍 Checking permission: role=" + roleName +
                ", menu=" + requestedMenu +
                ", action=" + checkPermission.action());

        boolean allowed = authorizationService.hasPermission(roleName, requestedMenu, checkPermission.action());

        // Intento adicional si el endpoint empieza con /api
        if (!allowed) {
            allowed = authorizationService.hasPermission(roleName, requestedMenu.replace("/api", ""), checkPermission.action());
        }

        if (!allowed) {
            throw new SecurityException("Access denied: " + checkPermission.menu() + " / " + checkPermission.action());
        }

        System.out.println("✅ Access GRANTED for role=" + roleName +
                " on menu=" + requestedMenu +
                " with action=" + checkPermission.action());
    }
}
