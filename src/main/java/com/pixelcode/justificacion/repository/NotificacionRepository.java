package com.pixelcode.justificacion.repository;

import com.pixelcode.justificacion.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    /**
     * Find all notifications for a user
     */
    List<Notificacion> findByUsuarioId(Long usuarioId);
    
    /**
     * Find notifications by sending status
     */
    List<Notificacion> findByEnviado(Boolean enviado);
    
    /**
     * Find notifications for user by status
     */
    List<Notificacion> findByUsuarioIdAndEnviado(Long usuarioId, Boolean enviado);
}