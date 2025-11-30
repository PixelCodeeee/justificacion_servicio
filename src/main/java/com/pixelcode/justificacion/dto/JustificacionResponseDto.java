// /dto/JustificacionResponseDto.java
package com.pixelcode.justificacion.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class JustificacionResponseDto {
    private Long id;
    private Long alumnoId;
    private Long asistenciaId;
    private String motivo;
    private String archivoUrl;
    private String estado;
    private LocalDateTime fechaSolicitud;
    private LocalDateTime fechaRevision;
    private Long revisadoPor;
}
