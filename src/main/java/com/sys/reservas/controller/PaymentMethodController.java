package com.sys.reservas.controller;
import com.sys.reservas.dto.request.PaymentMethodRequest;
import com.sys.reservas.dto.response.PaymentMethodResponse;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.dto.response.RoleResponse;
import com.sys.reservas.security.CheckPermission;
import com.sys.reservas.service.PaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/PaymentMethods")
@RequiredArgsConstructor
public class PaymentMethodController {

private  final PaymentMethodService service;

    @PostMapping
    @CheckPermission(menu = "/MetodoPago", action = "CREATE")
    public ResponseEntity<ResponseBase<PaymentMethodResponse>> create(@RequestBody @Valid PaymentMethodRequest request){
        return  service.create(request);
    }
    @PutMapping("/{id}")
    @CheckPermission(menu = "/MetodoPago", action = "UPDATE")
    public ResponseEntity<ResponseBase<PaymentMethodResponse>> update(@PathVariable Long id, @RequestBody @Valid  PaymentMethodRequest request) {
        return service.update(id, request);
    }
    @DeleteMapping("/{id}")
    @CheckPermission(menu = "/MetodoPago", action = "DELETE")
    public ResponseEntity<ResponseBase<Void>> delete(@PathVariable Long id) {
        return service.delete(id);
    }
    @GetMapping
    @CheckPermission(menu = "/MetodoPago", action = "READ")
    public ResponseEntity<ResponseBase<Page<PaymentMethodResponse>>> list(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String globalFilter
    ) {
        return service.findAll(page, size, sortField, sortOrder, globalFilter);
    }


}

