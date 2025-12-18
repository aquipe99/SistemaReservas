package com.sys.reservas.service;




import com.sys.reservas.config.CustomUserDetails;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        Authentication authentication;
        try {
            authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        }
        catch (BadCredentialsException e){

            throw new BadCredentialsException("Credenciales incorrectas");


        }


        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails,user.getRole().getName());

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
    public ResponseBase<UserResponse> getCurrentUser(Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("Usuario no autenticado");
        }

        String email = authentication.getName(); // viene del JWT

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        List<MenuPermissionResponse> flatMenus =
                roleMenuRepository.findMenusWithPermissionsByRoleId(user.getRole().getId());

        List<MenuPermissionResponse> menuTree = buildMenuTree(flatMenus);

        UserResponse userDto = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                menuTree
        );

        return new ResponseBase<>(200, "Usuario actual", Optional.of(userDto));
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
        root.sort(Comparator.comparing(MenuPermissionResponse::getOrder));
        map.values().forEach(m -> m.getItems().sort(Comparator.comparing(MenuPermissionResponse::getOrder)));
        return root;
    }

}
