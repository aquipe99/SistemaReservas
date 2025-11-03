package com.sys.reservas.controller;

import com.sys.reservas.security.CheckPermission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping
    @CheckPermission(menu = "/users", action = "READ")
    public String getAllUsers() {
        return "List of users (only ADMIN can see this)";
    }


    @PostMapping
    @CheckPermission(menu = "/users", action = "CREATE")
    public String createUser() {
        return "User created";
    }
}
