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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService implements PaymentMethodImpl {

    private final PaymentMethodRepository repository;
    private final PaymentMethodMapper mapper;
    @Override
    public ResponseBase<Page<PaymentMethodResponse>> findAll(int page, int size, String sortField, String sortOrder, String globalFilter) {
        Sort sort = Sort.by(sortField == null ? "id": sortField);
        sort ="desc".equalsIgnoreCase(sortOrder)
                ? sort.descending()
                : sort.ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Specification<PaymentMethod> spec = PaymentMethodSpecification.globalFilter(globalFilter);

        Page<PaymentMethod> paymentMethods = repository.findAll(spec,pageable);

        Page<PaymentMethodResponse> mappedPage = paymentMethods.map(mapper::toResponse);
        return new ResponseBase<>(200, "Listado correcto", Optional.of(mappedPage));

    }

    @Override
    public ResponseBase<PaymentMethodResponse> create(PaymentMethodRequest request) {
        if(repository.findByName(request.getName()).isPresent())
        {
            return new ResponseBase<>(400, "El nombre ya existe", Optional.empty());

        }
        PaymentMethod paymentMethod = mapper.toEntity(request);
        paymentMethod.setCreatedBy(SecurityUtils.getCurrentUserId());
        paymentMethod.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC)); // createat

        PaymentMethod saved = repository.save(paymentMethod);
        return new ResponseBase<>(200,"Creado Correctamente",Optional.of(mapper.toResponse(saved)));
    }
    //OffsetDateTime utc = paymentMethod.getCreatedAt();
    //ZonedDateTime peruTime = utc.atZoneSameInstant(ZoneId.of("America/Lima"));
    @Override
    public ResponseBase<PaymentMethodResponse> update(Long id, PaymentMethodRequest request) {
        Optional<PaymentMethod> existing = repository.findById(id);
        if(existing.isEmpty()){
            return new ResponseBase<>(404,"Rol no ecnontrado",Optional.empty());
        }
        PaymentMethod paymentMethod = existing.get();
        paymentMethod.setName(request.getName());
        PaymentMethod updated = repository.save(paymentMethod);
        return new ResponseBase<>(200,"Rol actulizado correctamente",Optional.of(mapper.toResponse(updated)));
    }

    @Override
    public ResponseBase<Void> delete(Long id) {
        if(!repository.existsById(id)){
            return new ResponseBase<>(404, "Rol no encontrado", Optional.empty());
        }
        repository.deleteById(id);
        return new ResponseBase<>(200, "Rol eliminado correctamente", Optional.empty());
    }
}
