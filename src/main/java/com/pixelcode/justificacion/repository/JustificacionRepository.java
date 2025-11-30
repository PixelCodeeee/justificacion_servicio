// /repository/JustificacionRepository.java
package com.pixelcode.justificacion.repository;

import com.pixelcode.justificacion.entity.Justificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JustificacionRepository extends JpaRepository<Justificacion, Long> {
    List<Justificacion> findByAlumnoId(Long alumnoId);
    List<Justificacion> findByAsistenciaId(Long asistenciaId);
}
