package com.sys.reservas.service.Impl;

import com.sys.reservas.dto.request.CourtRequest;
import com.sys.reservas.dto.response.CourtResponse;
import com.sys.reservas.dto.response.ResponseBase;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CourtServiceImpl {

    ResponseEntity<ResponseBase<Page<CourtResponse>>> findAll(int page,
                                                              int size,
                                                              String sortField,
                                                              String sortOrder,
                                                              String globalFilter);
    ResponseEntity<ResponseBase<CourtResponse>> create (CourtRequest request);
    ResponseEntity<ResponseBase<CourtResponse>> update(Long id,CourtRequest request);
    ResponseEntity<ResponseBase<Void>> delete(Long id);
}
