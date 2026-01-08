package com.sys.reservas.mapper;

import com.sys.reservas.dto.request.CourtRequest;
import com.sys.reservas.dto.response.CourtResponse;
import com.sys.reservas.entity.Court;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CourtMapper {
    CourtMapper INSTANCE = Mappers.getMapper(CourtMapper.class);
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    Court toEntity(CourtRequest request);
    CourtResponse toResponse(Court court);
}
