package com.pixelcode.justificacion.repository;

import com.pixelcode.justificacion.entity.Justificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificacionRepository extends JpaRepository<Justificacion, Long> {
    
    /**
     * Find all justifications for a student
     */
    List<Justificacion> findByAlumnoId(Long alumnoId);
    
    /**
     * Find justifications by attendance ID
     */
    List<Justificacion> findByAsistenciaId(Long asistenciaId);
    
    /**
     * Find justifications by status
     */
    List<Justificacion> findByEstado(String estado);
    
    /**
     * Find justifications reviewed by a specific user
     */
    List<Justificacion> findByRevisadoPor(Long revisadoPor);
    
    /**
     * Find pending justifications for a student
     */
    List<Justificacion> findByAlumnoIdAndEstado(Long alumnoId, String estado);
}