// /service/JustificacionService.java
package com.pixelcode.justificacion.service;

import com.pixelcode.justificacion.dto.*;
import com.pixelcode.justificacion.entity.*;
import com.pixelcode.justificacion.repository.JustificacionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JustificacionService {

    private final JustificacionRepository repo;
    private final NotificacionService notificacionService;
    private final Path uploadDir;
    private final long maxFileBytes;
    private final List<String> allowedTypes;

    public JustificacionService(JustificacionRepository repo,
                                NotificacionService notificacionService,
                                @Value("${app.upload-dir}") String uploadDir,
                                @Value("${app.max-file-size}") String maxFileSize,
                                @Value("${app.allowed-types}") String allowedTypes) throws IOException {
        this.repo = repo;
        this.notificacionService = notificacionService;
        this.uploadDir = Paths.get(uploadDir);
        if (!Files.exists(this.uploadDir)) Files.createDirectories(this.uploadDir);

        // parse size like "5MB"
        this.maxFileBytes = parseToBytes(maxFileSize);
        this.allowedTypes = List.of(allowedTypes.split(","));
    }

    // util parse
    private long parseToBytes(String text) {
        text = text.toUpperCase().trim();
        if (text.endsWith("MB")) {
            return Long.parseLong(text.replace("MB","").trim()) * 1024 * 1024;
        } else if (text.endsWith("KB")) {
            return Long.parseLong(text.replace("KB","").trim()) * 1024;
        } else {
            return Long.parseLong(text);
        }
    }

    // create upload
/*public Justificacion crear(CrearJustificacionDto dto, MultipartFile file) throws IOException {
    if (file == null || file.isEmpty()) {
        throw new IllegalArgumentException("Archivo requerido");
    }
    
    if (file.getSize() > maxFileBytes) {
        throw new IllegalArgumentException("Archivo excede tamaño máximo");
    }

    String ct = file.getContentType();
    if (ct == null || allowedTypes.stream().noneMatch(allowed -> allowed.equalsIgnoreCase(ct))) {
        throw new IllegalArgumentException("Tipo de archivo no permitido: " + ct);
    }

        String filename = System.currentTimeMillis() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
        Path target = uploadDir.resolve(filename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        Justificacion j = Justificacion.builder()
                .alumnoId(dto.getAlumnoId())
                .asistenciaId(dto.getAsistenciaId())
                .motivo(dto.getMotivo())
                .archivoUrl(target.toAbsolutePath().toString())
                .estado("PENDIENTE")
                .fechaSolicitud(LocalDateTime.now())
                .build();

        Justificacion saved = repo.save(j);

        // opcional: notificar al tutor (si conoces tutorId, aquí deberías buscar)
        // notificacionService.enviar(tutorId, "Nueva justificación pendiente...");

        return saved;
    }*/
public Justificacion crear(CrearJustificacionDto dto, MultipartFile file) throws IOException {
    String archivoUrl = null;
    
    // Procesar archivo solo si existe
    if (file != null && !file.isEmpty()) {
        // validaciones de archivo...
        if (file.getSize() > maxFileBytes) {
            throw new IllegalArgumentException("Archivo excede tamaño máximo");
        }

        String ct = file.getContentType();
        if (ct == null || allowedTypes.stream().noneMatch(a -> a.equalsIgnoreCase(ct))) {
            throw new IllegalArgumentException("Tipo de archivo no permitido: " + ct);
        }

        String filename = System.currentTimeMillis() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
        Path target = uploadDir.resolve(filename);
        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
        archivoUrl = target.toAbsolutePath().toString();
    }

    Justificacion j = Justificacion.builder()
            .alumnoId(dto.getAlumnoId())
            .asistenciaId(dto.getAsistenciaId())
            .motivo(dto.getMotivo())
            .archivoUrl(archivoUrl)  // puede ser null
            .estado("PENDIENTE")
            .fechaSolicitud(LocalDateTime.now())
            .build();

    return repo.save(j);
}
//
    public Optional<Justificacion> obtener(Long id) {
    return repo.findById(id);
}

    public List<Justificacion> listarPorAlumno(Long alumnoId) {
    if (alumnoId == null) {
        throw new IllegalArgumentException("alumnoId no puede ser nulo");
    }
    return repo.findByAlumnoId(alumnoId);
}

    public Justificacion editar(Long id, CrearJustificacionDto dto) {
        Justificacion j = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe"));
        if (!"PENDIENTE".equalsIgnoreCase(j.getEstado())) {
            throw new IllegalStateException("No se puede editar una justificación ya revisada");
        }
        j.setMotivo(dto.getMotivo());
        return repo.save(j);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public Justificacion revisar(Long id, RevisarDto revisarDto) {
        Justificacion j = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("No existe"));
        if (!"PENDIENTE".equalsIgnoreCase(j.getEstado())) {
            throw new IllegalStateException("Ya fue revisado");
        }
        String nuevo = revisarDto.getEstado().toUpperCase();
        if (!nuevo.equals("APROBADA") && !nuevo.equals("RECHAZADA")) {
            throw new IllegalArgumentException("Estado inválido");
        }

        j.setEstado(nuevo);
        j.setFechaRevision(LocalDateTime.now());
        j.setRevisadoPor(revisarDto.getRevisadoPor());
        Justificacion saved = repo.save(j);

        // Actualizar asistencia: si está integrada, aquí harías llamada a AsistenciasService para setear estado JUSTIFICADO
        if ("APROBADA".equals(nuevo)) {
            // Llamada a servicio de asistencias para actualizar: ejemplo teórico
            // asistenciaService.marcarJustificado(j.getAsistenciaId());
        }

        // Notificar al alumno
        notificacionService.enviar(j.getAlumnoId(), "Tu justificación (id:" + j.getId() + ") ha sido " + nuevo);

        return saved;
    }
}
