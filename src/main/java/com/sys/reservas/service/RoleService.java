package com.sys.reservas.service;





import com.sys.reservas.dto.request.RoleRequest;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.dto.response.RoleResponse;
import com.sys.reservas.entity.Role;
import com.sys.reservas.mapper.RoleMapper;
import com.sys.reservas.repository.RoleRepository;
import com.sys.reservas.service.Impl.RoleServiceImpl;
import com.sys.reservas.specification.RoleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service

@RequiredArgsConstructor
public class RoleService   implements RoleServiceImpl {
    private final RoleRepository repository;
    private final RoleMapper mapper;

    @Override
    public ResponseBase<RoleResponse> create(RoleRequest request) {
        if(repository.findByName(request.getName()).isPresent())
        {
            return new ResponseBase<>(400, "El rol ya existe", Optional.empty());

        }
        Role role = mapper.toEntity(request);
        Role saved = repository.save(role);
        return new ResponseBase<>(200, "Rol creado correctamente", Optional.of(mapper.toResponse(saved)));
    }

    @Override
    public ResponseBase<RoleResponse> update(Long id, RoleRequest request) {
        Optional<Role> existing = repository.findById(id);
        if(existing.isEmpty()){
            return new ResponseBase<>(404,"Rol no ecnontrado",Optional.empty());
        }
        Role role = existing.get();
        role.setName(request.getName());
        Role updated = repository.save(role);
        return new ResponseBase<>(200,"Rol actulizado correctamente",Optional.of(mapper.toResponse(updated)));
    }

    @Override
    public ResponseBase<Page<RoleResponse>> list(String name,  int page, int size, String sortField, String sortDir) {
        Sort sort = Sort.by(sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Role> spec = Specification.allOf(RoleSpecification.hasNameLike(name));

        Page<Role> roles = repository.findAll(spec, pageable);
        Page<RoleResponse> mappedPage = roles.map(mapper::toResponse);

        return new ResponseBase<>(200, "Listado correcto", Optional.of(mappedPage));
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
