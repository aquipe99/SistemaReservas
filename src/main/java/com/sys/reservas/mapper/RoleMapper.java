package com.sys.reservas.mapper;
import com.sys.reservas.dto.request.RoleRequest;
import com.sys.reservas.dto.response.RoleResponse;
import com.sys.reservas.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.List;



//@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    Role toEntity(RoleRequest request);
    RoleResponse toResponse(Role role);
    List<RoleResponse> toResponseList(List<Role> roles);
}
