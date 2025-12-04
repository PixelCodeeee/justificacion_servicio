package com.pixelcode.justificacion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad Justificacion
 * Representa una justificación de ausencia presentada por un alumno
 * 
 * MICROSERVICES PATTERN:
 * - asistenciaId references asistencia-service (will be created)
 * - alumnoId references usuarios-service (ALUMNO)
 * - revisadoPor references usuarios-service (TUTOR or MAESTRO)
 */
@Entity
@Table(name = "justificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Justificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to Alumno in usuarios-service
     */
    @Column(name = "alumno_id", nullable = false)
    private Long alumnoId;

    /**
     * Reference to Asistencia in asistencia-service
     */
    @Column(name = "asistencia_id", nullable = false)
    private Long asistenciaId;

    /**
     * Reason for absence
     */
    @Column(length = 1000)
    private String motivo;

    /**
     * File path or URL for supporting document
     */
    @Column(name = "archivo_url", length = 500)
    private String archivoUrl;

    /**
     * Status: PENDIENTE, APROBADA, RECHAZADA
     */
    @Column(nullable = false, length = 50)
    private String estado = "PENDIENTE";

    /**
     * When the justification was submitted
     */
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDateTime fechaSolicitud;

    /**
     * When it was reviewed
     */
    @Column(name = "fecha_revision")
    private LocalDateTime fechaRevision;

    /**
     * Who reviewed it (Tutor or Maestro ID from usuarios-service)
     */
    @Column(name = "revisado_por")
    private Long revisadoPor;
    
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
        if (fechaSolicitud == null) {
            fechaSolicitud = LocalDateTime.now();
        }
        if (estado == null) {
            estado = "PENDIENTE";
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}