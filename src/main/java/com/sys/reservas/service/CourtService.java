package com.sys.reservas.service;

import com.sys.reservas.config.SecurityUtils;
import com.sys.reservas.dto.request.CourtRequest;
import com.sys.reservas.dto.response.CourtResponse;
import com.sys.reservas.dto.response.PaymentMethodResponse;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.entity.Court;
import com.sys.reservas.mapper.CourtMapper;
import com.sys.reservas.repository.CourtRepository;
import com.sys.reservas.service.Impl.CourtServiceImpl;
import com.sys.reservas.specification.CourtSpecification;
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
public class CourtService  implements CourtServiceImpl {

    private final CourtRepository repository;
    private final CourtMapper mapper;

    @Override
    public ResponseEntity<ResponseBase<Page<CourtResponse>>> findAll(int page, int size, String sortField, String sortOrder, String globalFilter) {
        Sort sort = Sort.by(sortField == null ? "id": sortField);
        sort ="desc".equalsIgnoreCase(sortOrder)
                ? sort.descending()
                : sort.ascending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Specification<Court> spec = CourtSpecification.globalFilter(globalFilter);
        Page<Court> courts = repository.findAll(spec,pageable);
        Page<CourtResponse> mappedPage = courts.map(mapper::toResponse);

        return ResponseEntity.ok(new ResponseBase<>(200, "Listado correcto", Optional.of(mappedPage)));
    }

    @Override
    public ResponseEntity<ResponseBase<CourtResponse>> create(CourtRequest request) {
        String normalizedName = request.getName()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();

        if(repository.findByName(normalizedName).isPresent()){
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "El nombre ya existe");
            return ResponseEntity.badRequest().body(
                    new ResponseBase<>(400, "Error de validación", errors)
            );
        }

        Court court = mapper.toEntity(request);
        court.setName(normalizedName);
        court.setCreatedBy(SecurityUtils.getCurrentUserId());
        court.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));

        Court saved = repository.save(court);

        return ResponseEntity.ok(new ResponseBase<>(200, "Creado correctamente", Optional.of(mapper.toResponse(saved))));
    }

    @Override
    public ResponseEntity<ResponseBase<CourtResponse>> update(Long id, CourtRequest request) {
        Optional<Court> existing = repository.findById(id);
        if(existing.isEmpty()){
            ResponseBase<CourtResponse> response = new ResponseBase<>(404, "Registro no encontrado", Optional.empty());
            return ResponseEntity.status(404).body(response);
        }
        String normalizedName = request.getName()
                .trim()
                .replaceAll("\\s+", " ")
                .toUpperCase();
        if(repository.findByNameAndIdNot(normalizedName, id).isPresent()){
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "El nombre ya existe");
            return ResponseEntity.badRequest().body(
                    new ResponseBase<>(400, "Error de validación", errors)
            );
        }
        Court court = existing.get();
        court.setName(normalizedName);
        court.setDescription(request.getDescription());
        court.setStatus(request.getStatus());
        court.setModifiedBy(SecurityUtils.getCurrentUserId());
        court.setModifiedAt(OffsetDateTime.now(ZoneOffset.UTC));

        Court updated = repository.save(court);

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
