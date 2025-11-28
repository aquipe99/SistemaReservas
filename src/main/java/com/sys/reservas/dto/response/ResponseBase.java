package com.sys.reservas.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;


@Getter
@Setter
public class ResponseBase<T> {
    private int codigo;
    private String mensaje;
    private Optional<T> data;

    public ResponseBase(int codigo, String mensaje, Optional<T> data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = data;
    }

    public ResponseBase() { }
}
