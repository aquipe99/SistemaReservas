package com.sys.reservas.service;

import com.sys.reservas.config.CustomUserDetails;
import com.sys.reservas.entity.User;
import com.sys.reservas.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return new CustomUserDetails(
                user.getId().intValue(),
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(
                        () -> "ROLE_" + user.getRole().getName().toUpperCase()
                )
        );
//        return org.springframework.security.core.userdetails.User.builder()
//                .username(user.getEmail())
//                .password(user.getPassword())
//                .authorities(Collections.singletonList(() -> "ROLE_" + user.getRole().getName().toUpperCase()))
//                .build();
    }
}
