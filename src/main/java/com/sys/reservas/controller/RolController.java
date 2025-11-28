package com.sys.reservas.controller;

import com.sys.reservas.dto.request.RoleRequest;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.dto.response.RoleResponse;
import com.sys.reservas.security.CheckPermission;
import com.sys.reservas.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {
 private final RoleService service;
    @PostMapping
    @CheckPermission(menu = "/roles", action = "CREATE")
    public ResponseBase<RoleResponse> create(@RequestBody RoleRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @CheckPermission(menu = "/roles", action = "UPDATE")
    public ResponseBase<RoleResponse> update(@PathVariable Long id, @RequestBody RoleRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(menu = "/roles", action = "DELETE")
    public ResponseBase<Void> delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @GetMapping
    @CheckPermission(menu = "/roles", action = "READ")
    public ResponseBase<Page<RoleResponse>> list(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return service.list(name, page, size, sortField, sortDir);
    }

}
