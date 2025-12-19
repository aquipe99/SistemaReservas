package com.sys.reservas.service;

import com.sys.reservas.config.SecurityUtils;
import com.sys.reservas.dto.request.PaymentMethodRequest;
import com.sys.reservas.dto.response.PaymentMethodResponse;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.entity.PaymentMethod;

import com.sys.reservas.mapper.PaymentMethodMapper;
import com.sys.reservas.repository.PaymentMethodRepository;
import com.sys.reservas.service.Impl.PaymentMethodImpl;
import com.sys.reservas.specification.PaymentMethodSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService implements PaymentMethodImpl {

    private final PaymentMethodRepository repository;
    private final PaymentMethodMapper mapper;
    @Override
    public ResponseEntity<ResponseBase<Page<PaymentMethodResponse>>> findAll(int page, int size, String sortField, String sortOrder, String globalFilter) {
        Sort sort = Sort.by(sortField == null ? "id": sortField);
        sort ="desc".equalsIgnoreCase(sortOrder)
                ? sort.descending()
                : sort.ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Specification<PaymentMethod> spec = PaymentMethodSpecification.globalFilter(globalFilter);

        Page<PaymentMethod> paymentMethods = repository.findAll(spec,pageable);

        Page<PaymentMethodResponse> mappedPage = paymentMethods.map(mapper::toResponse);
        return ResponseEntity.ok(new ResponseBase<>(200, "Listado correcto", Optional.of(mappedPage)));

    }
    @Override
    public ResponseEntity<ResponseBase<PaymentMethodResponse>> create(PaymentMethodRequest request) {
        if(repository.findByName(request.getName()).isPresent())
        {
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "El nombre ya existe");
            return ResponseEntity.badRequest().body(
                    new ResponseBase<>(400, "Error de validación", errors)
            );
        }
        PaymentMethod paymentMethod = mapper.toEntity(request);
        paymentMethod.setCreatedBy(SecurityUtils.getCurrentUserId());
        paymentMethod.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC)); // createat

        PaymentMethod saved = repository.save(paymentMethod);
        return ResponseEntity.ok(new ResponseBase<>(200, "Creado correctamente", Optional.of(mapper.toResponse(saved))));
    }
    //OffsetDateTime utc = paymentMethod.getCreatedAt();
    //ZonedDateTime peruTime = utc.atZoneSameInstant(ZoneId.of("America/Lima"));
    @Override
    public ResponseEntity<ResponseBase<PaymentMethodResponse>> update(Long id, PaymentMethodRequest request) {
        Optional<PaymentMethod> existing = repository.findById(id);
        if(existing.isEmpty()){
            ResponseBase<PaymentMethodResponse> response = new ResponseBase<>(404, "Registro no encontrado", Optional.empty());
            return ResponseEntity.status(404).body(response);
        }
        if(repository.findByName(request.getName()).isPresent())
        {
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "El nombre ya existe");
            return ResponseEntity.badRequest().body(
                    new ResponseBase<>(400, "Error de validación", errors)
            );
        }
        PaymentMethod paymentMethod = existing.get();
        paymentMethod.setName(request.getName());
        paymentMethod.setStatus(request.getStatus());
        PaymentMethod updated = repository.save(paymentMethod);
        return ResponseEntity.ok(new ResponseBase<>(200, "Registro actualizado correctamente", Optional.of(mapper.toResponse(updated))));
    }

    @Override
    public ResponseEntity<ResponseBase<Void>> delete(Long id) {
        if(!repository.existsById(id)){
            ResponseBase<Void> response = new ResponseBase<>(404, "Registro no encontrado", Optional.empty());
            return ResponseEntity.status(404).body(response);
        }
        repository.deleteById(id);
        return ResponseEntity.ok(new ResponseBase<>(200, "Registro eliminado correctamente", Optional.empty()));
    }

}
