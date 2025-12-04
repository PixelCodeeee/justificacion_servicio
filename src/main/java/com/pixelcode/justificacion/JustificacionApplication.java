package com.pixelcode.justificacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Microservicio de Justificaciones
 * Gestiona justificaciones de ausencias con soporte de archivos
 * 
 * @version 2.0 - MySQL & Eureka Integration
 */
@SpringBootApplication
@EnableDiscoveryClient
public class JustificacionApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustificacionApplication.class, args);
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                            ║");
        System.out.println("║       MICROSERVICIO DE JUSTIFICACIONES INICIADO            ║");
        System.out.println("║                                                            ║");
        System.out.println("║  Servicio: justificacion-service                           ║");
        System.out.println("║  Puerto: 8087                                              ║");
        System.out.println("║  Database: Clever Cloud MySQL                              ║");
        System.out.println("║  Eureka: http://localhost:8761                             ║");
        System.out.println("║  API Base: http://localhost:8087/api/justificaciones       ║");
        System.out.println("║                                                            ║");
        System.out.println("║  Endpoints disponibles:                                    ║");
        System.out.println("║  • POST   /api/justificaciones        [Crear + archivo]    ║");
        System.out.println("║  • GET    /api/justificaciones/{id}   [Obtener]            ║");
        System.out.println("║  • GET    /alumno/{id}                [Por alumno]         ║");
        System.out.println("║  • PUT    /api/justificaciones/{id}   [Editar]             ║");
        System.out.println("║  • PATCH  /{id}/aprobar               [Aprobar]            ║");
        System.out.println("║  • PATCH  /{id}/rechazar              [Rechazar]           ║");
        System.out.println("║  • DELETE /api/justificaciones/{id}   [Eliminar]           ║");
        System.out.println("║                                                            ║");
        System.out.println("║  File Upload: uploads/ directory                           ║");
        System.out.println("║  Max Size: 5MB | Types: PDF, PNG, JPEG                    ║");
        System.out.println("║                                                            ║");
        System.out.println("║  Vía API Gateway:                                          ║");
        System.out.println("║  http://localhost:8080/justificacion-service/api/...       ║");
        System.out.println("║                                                            ║");
        System.out.println("║  📎 7 Endpoints + File Upload Support ✓                    ║");
        System.out.println("║  🎓 UTEQ - Sistema de Asistencias 2025                     ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
}