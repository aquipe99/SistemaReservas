package com.sys.reservas.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourtRequest {
    @NotBlank(message = "El nombre es requerido")
    @Size(max = 20,message = "El nombre no debe superar 20 caracteres")
    private String name;
    @Size(max = 100,message = "La descripcion no debe superar 100 caracteres")
    private String description;
    @NotNull(message = "El estado es requerido")
    private Boolean status;
}
