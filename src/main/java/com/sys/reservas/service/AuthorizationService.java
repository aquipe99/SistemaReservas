package com.sys.reservas.service;

import com.sys.reservas.repository.RoleMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final RoleMenuRepository roleMenuRepository;
    public boolean hasPermission(String roleName, String menu, String action) {
        // Este método usa el repo para verificar si el rol tiene permiso sobre ese menú y acción
        return roleMenuRepository.existsByRoleNameAndMenuAndAction(roleName, menu, action);
    }
}
