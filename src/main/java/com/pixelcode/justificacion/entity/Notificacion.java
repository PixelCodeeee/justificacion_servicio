package com.pixelcode.justificacion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad Notificacion
 * Sistema de notificaciones para usuarios
 * 
 * MICROSERVICES PATTERN:
 * - usuarioId references usuarios-service
 */
@Entity
@Table(name = "notificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Reference to Usuario in usuarios-service
     */
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    /**
     * Notification channel: EMAIL, INTERNAL, SMS
     */
    @Column(nullable = false, length = 50)
    private String canal = "INTERNAL";

    /**
     * Notification message
     */
    @Column(nullable = false, length = 1000)
    private String mensaje;

    /**
     * Whether notification was sent successfully
     */
    @Column(nullable = false)
    private Boolean enviado = false;

    /**
     * When notification was sent
     */
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
    
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (canal == null) {
            canal = "INTERNAL";
        }
        if (enviado == null) {
            enviado = false;
        }
    }
}