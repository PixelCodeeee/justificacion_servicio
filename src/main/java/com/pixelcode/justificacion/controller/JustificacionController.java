// /controller/JustificacionController.java
package com.pixelcode.justificacion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pixelcode.justificacion.dto.*;
import com.pixelcode.justificacion.entity.Justificacion;
import com.pixelcode.justificacion.service.JustificacionService;
import org.springframework.http.*;
//import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/justificaciones")
public class JustificacionController {

    private final JustificacionService service;

    public JustificacionController(JustificacionService service) {
        this.service = service;
    }
/*--------------- */
@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> crear(
    @RequestPart("meta") String metaJson,  // Recibir como String
    @RequestPart("archivo") MultipartFile archivo) {
    
    try {
        // Convertir el JSON manualmente
        ObjectMapper objectMapper = new ObjectMapper();
        CrearJustificacionDto meta = objectMapper.readValue(metaJson, CrearJustificacionDto.class);
        
        Justificacion j = service.crear(meta, archivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(j);
    } catch (Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
    /*@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crear(@Validated @RequestPart("meta") CrearJustificacionDto meta,
                                   @RequestPart("archivo") MultipartFile archivo) {
        try {
            Justificacion j = service.crear(meta, archivo);
            return ResponseEntity.status(HttpStatus.CREATED).body(j);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        return service.obtener(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","No existe")));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<?> listarPorAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(service.listarPorAlumno(alumnoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody CrearJustificacionDto dto) {
        try {
            Justificacion updated = service.editar(id, dto);
            return ResponseEntity.ok(updated);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobar(@PathVariable Long id, @RequestBody RevisarDto dto) {
        try {
            dto.setEstado("APROBADA");
            Justificacion j = service.revisar(id, dto);
            return ResponseEntity.ok(j);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    @PatchMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazar(@PathVariable Long id, @RequestBody RevisarDto dto) {
        try {
            dto.setEstado("RECHAZADA");
            Justificacion j = service.revisar(id, dto);
            return ResponseEntity.ok(j);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
