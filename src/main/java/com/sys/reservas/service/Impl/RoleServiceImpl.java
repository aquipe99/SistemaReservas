package com.sys.reservas.service.Impl;

import com.sys.reservas.dto.request.RoleRequest;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.dto.response.RoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleServiceImpl {

    ResponseBase<Page<RoleResponse>> list(String name, int page, int size, String sortField, String sortDir);
    ResponseBase<RoleResponse> create (RoleRequest request);
    ResponseBase<RoleResponse> update(Long id,RoleRequest request);
    ResponseBase<Void> delete(Long id);
}
