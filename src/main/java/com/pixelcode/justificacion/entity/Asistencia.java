// /entity/Asistencia.java
package com.pixelcode.justificacion.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "asistencia")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Asistencia {
    @Id
    private Long id; // asumimos id externo; si local, @GeneratedValue

    private Long alumnoId;
    private Long grupoId;
    private Long materiaId;
    private String estado; // "PRESENTE", "AUSENTE", "JUSTIFICADO"
}
