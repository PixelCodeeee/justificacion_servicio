// /repository/NotificacionRepository.java
package com.pixelcode.justificacion.repository;

import com.pixelcode.justificacion.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {}
