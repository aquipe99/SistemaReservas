package com.sys.reservas.controller;

import com.sys.reservas.dto.request.CourtRequest;
import com.sys.reservas.dto.request.PaymentMethodRequest;
import com.sys.reservas.dto.response.CourtResponse;
import com.sys.reservas.dto.response.PaymentMethodResponse;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.security.CheckPermission;
import com.sys.reservas.service.CourtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Court")
@RequiredArgsConstructor
public class CourtController {
    private final CourtService service;

    @PostMapping
    @CheckPermission(menu = "/Cancha", action = "CREATE")
    public ResponseEntity<ResponseBase<CourtResponse>> create(@RequestBody @Valid CourtRequest request){
        return  service.create(request);
    }

    @PutMapping("/{id}")
    @CheckPermission(menu = "/Cancha", action = "UPDATE")
    public ResponseEntity<ResponseBase<CourtResponse>> update(@PathVariable Long id, @RequestBody @Valid  CourtRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @CheckPermission(menu = "/Cancha", action = "DELETE")
    public ResponseEntity<ResponseBase<Void>> delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @GetMapping
    @CheckPermission(menu = "/Cancha", action = "READ")
    public ResponseEntity<ResponseBase<Page<CourtResponse>>> list(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String globalFilter
    ) {
        return service.findAll(page, size, sortField, sortOrder, globalFilter);
    }

}
