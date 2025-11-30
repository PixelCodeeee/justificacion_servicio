// /entity/Notificacion.java
package com.pixelcode.justificacion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;

    private String canal; // EMAIL, INTERNAL

    @Column(length = 1000)
    private String mensaje;

    private Boolean enviado;

    private LocalDateTime fechaEnvio;
}
