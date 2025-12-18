package com.sys.reservas.controller;

import com.sys.reservas.config.JwtUtils;
import com.sys.reservas.dto.request.LoginRequest;
import com.sys.reservas.dto.response.LoginResponse;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.dto.response.UserResponse;
import com.sys.reservas.entity.User;
import com.sys.reservas.repository.UserRepository;
import com.sys.reservas.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;


    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //String encodedPassword = encoder.encode("admin123");
        //System.out.println(encodedPassword);
        return authService.login(request);
    }

    @GetMapping("/me")
    public ResponseBase<UserResponse> me(Authentication authentication) {
        return authService.getCurrentUser(authentication);
    }
}
