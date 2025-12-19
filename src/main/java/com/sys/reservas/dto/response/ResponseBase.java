package com.sys.reservas.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Optional;


@Getter
@Setter
public class ResponseBase<T> {
    private int codigo;
    private String mensaje;
    private Map<String,String> errores;
    private Optional<T> data;


    public ResponseBase() { }

    public ResponseBase(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ResponseBase(int codigo, String mensaje, Optional<T> data) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.data = data;
    }

    public ResponseBase(int codigo, String mensaje, Map<String, String> errores) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.errores = errores;
    }
}
