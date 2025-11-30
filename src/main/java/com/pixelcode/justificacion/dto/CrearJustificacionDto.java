// /dto/CrearJustificacionDto.java
package com.pixelcode.justificacion.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CrearJustificacionDto {
    @NotNull
    private Long asistenciaId;

    @NotNull
    private Long alumnoId;

    private String motivo;
}
