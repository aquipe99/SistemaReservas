package com.sys.reservas.mapper;

import ch.qos.logback.core.model.ComponentModel;
import com.sys.reservas.dto.request.PaymentMethodRequest;
import com.sys.reservas.dto.response.PaymentMethodResponse;
import com.sys.reservas.entity.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {
    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    PaymentMethod toEntity(PaymentMethodRequest request);
    PaymentMethodResponse toResponse(PaymentMethod paymentMethod);
}
