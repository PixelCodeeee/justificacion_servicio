// /service/NotificacionService.java
package com.pixelcode.justificacion.service;

import com.pixelcode.justificacion.entity.Notificacion;
import com.pixelcode.justificacion.repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class NotificacionService {
    private final NotificacionRepository repo;

    public NotificacionService(NotificacionRepository repo) {
        this.repo = repo;
    }

   public void enviar(Long usuarioId, String mensaje) {
    System.out.println("ENVIANDO NOTIFICACION a usuario " + usuarioId + ": " + mensaje);

    Notificacion n = Notificacion.builder()
            .usuarioId(usuarioId)
            .canal("EMAIL")
            .mensaje(mensaje)
            .enviado(true)
            .fechaEnvio(LocalDateTime.now())
            .build();
    repo.save(n);
}
}
