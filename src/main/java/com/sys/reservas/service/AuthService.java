package com.sys.reservas.service;




import com.sys.reservas.config.JwtUtils;
import com.sys.reservas.dto.request.LoginRequest;
import com.sys.reservas.dto.response.*;
import com.sys.reservas.entity.Menu;
import com.sys.reservas.entity.User;
import com.sys.reservas.mapper.MenuMapper;
import com.sys.reservas.repository.RoleMenuRepository;
import com.sys.reservas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final RoleMenuRepository roleMenuRepository;
    private final MenuMapper menuMapper;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken((org.springframework.security.core.userdetails.User) authentication.getPrincipal(),user.getRole().getName());

        List<Menu> menus = roleMenuRepository.findMenusByRoleId(user.getRole().getId());

        List<MenuPermissionResponse> flatMenus  = roleMenuRepository.findMenusWithPermissionsByRoleId(user.getRole().getId());

        List<MenuPermissionResponse> menuTree = buildMenuTree(flatMenus);

        UserResponse userDto = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                menuTree
        );

        return new LoginResponse(token, userDto);
    }

    private List<MenuPermissionResponse> buildMenuTree(List<MenuPermissionResponse> menus) {
        Map<Long, MenuPermissionResponse> map = new HashMap<>();

        // Indexar por ID
        for (MenuPermissionResponse m : menus) {
            map.put(m.getId(), m);
        }

        List<MenuPermissionResponse> root = new ArrayList<>();

        // Construir árbol
        for (MenuPermissionResponse menu : menus) {
            if (menu.getParentMenuId() == null) {
                root.add(menu);
            } else {
                MenuPermissionResponse parent = map.get(menu.getParentMenuId().longValue());
                if (parent != null) {
                    parent.getItems().add(menu);
                }
            }
        }

        // Ordenar por menu_order
        root.sort(Comparator.comparing(MenuPermissionResponse::getOrder));
        map.values().forEach(m -> m.getItems().sort(Comparator.comparing(MenuPermissionResponse::getOrder)));

        return root;
    }

}
