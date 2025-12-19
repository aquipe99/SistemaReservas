package com.sys.reservas.excepcion;

import com.sys.reservas.dto.response.ResponseBase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //  Errores de validación (DTOs con @Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseBase<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ResponseBase<Map<String, String>> response = new ResponseBase<>(
                400,
                "Error de validación",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    //  Credenciales incorrectas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseBase<Object>> handleBadCredentials(BadCredentialsException ex) {

        ResponseBase<Object> response = new ResponseBase<>(
                401,
                "Credenciales incorrectas"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    //  acceso denegado
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseBase<Object>> handleAccessDenied(
            AccessDeniedException ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ResponseBase<>(403, ex.getMessage())
        );
    }
    //  Usuario no encontrado
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResponseBase<Object>> handleUserNotFound(UsernameNotFoundException ex) {

        ResponseBase<Object> response = new ResponseBase<>(
                404,
                "Usuario no encontrado"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //  Cualquier otra excepción genérica
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseBase<Object>> handleRuntime(RuntimeException ex) {

        ResponseBase<Object> response = new ResponseBase<>(
                500,
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
