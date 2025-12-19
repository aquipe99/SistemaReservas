package com.sys.reservas.service.Impl;


import com.sys.reservas.dto.request.PaymentMethodRequest;
import com.sys.reservas.dto.response.PaymentMethodResponse;
import com.sys.reservas.dto.response.ResponseBase;
import com.sys.reservas.entity.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface PaymentMethodImpl {
    ResponseEntity<ResponseBase<Page<PaymentMethodResponse>>> findAll(int page,
                                                                     int size,
                                                                     String sortField,
                                                                     String sortOrder,
                                                                     String globalFilter);
    ResponseEntity<ResponseBase<PaymentMethodResponse>> create (PaymentMethodRequest request);
    ResponseEntity<ResponseBase<PaymentMethodResponse>> update(Long id,PaymentMethodRequest request);
    ResponseEntity<ResponseBase<Void>> delete(Long id);
}
