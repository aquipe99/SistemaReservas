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
import java.util.List;



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

        List<MenuPermissionDTO> menuDtos = roleMenuRepository.findMenusWithPermissionsByRoleId(user.getRole().getId());

        UserResponse userDto = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                menuDtos
        );

        return new LoginResponse(token, userDto);
    }
}
