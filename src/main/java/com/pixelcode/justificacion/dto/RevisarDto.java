// /dto/RevisarDto.java
package com.pixelcode.justificacion.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RevisarDto {
    @NotNull
    private String estado; // "APROBADA" | "RECHAZADA"
    
    @NotNull
    private Long revisadoPor;
}
