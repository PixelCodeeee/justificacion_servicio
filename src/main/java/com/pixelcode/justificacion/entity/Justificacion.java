// /entity/Notificacion.java
package com.pixelcode.justificacion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "justificacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Justificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long alumnoId;

    @Column(nullable = false)
    private Long asistenciaId;

    private String motivo;

    private String archivoUrl; // path en filesystem

    private String estado; // PENDIENTE, APROBADA, RECHAZADA

    private LocalDateTime fechaSolicitud;

    private LocalDateTime fechaRevision;

    private Long revisadoPor; // tutor id
}
