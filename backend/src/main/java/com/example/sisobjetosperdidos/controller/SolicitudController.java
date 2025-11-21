package com.example.sisobjetosperdidos.controller;

import com.example.sisobjetosperdidos.dto.SolicitudRequest;
import com.example.sisobjetosperdidos.entity.ObjetoEntity;
import com.example.sisobjetosperdidos.entity.SolicitudEntity;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import com.example.sisobjetosperdidos.service.NotificacionService;
import com.example.sisobjetosperdidos.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final NotificacionService notificacionService;


    @PostMapping("/crear")
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudRequest request) {

        SolicitudEntity nuevaSolicitud = solicitudService.crearSolicitud(request);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud enviada correctamente");
        response.put("solicitud", nuevaSolicitud);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/estudiante/{id}")
    public ResponseEntity<?> listarPorEstudiante(@PathVariable Long id) {
        return ResponseEntity.ok(solicitudService.listarPorEstudiante(id));
    }

    // NUEVO ENDPOINT: LISTAR SOLICITUDES PENDIENTES (PANEL ADMIN)
    @GetMapping("/pendientes")
    public ResponseEntity<List<SolicitudEntity>> listarPendientes() {
        return ResponseEntity.ok(solicitudService.listarPendientes());
    }

    //  NUEVO ENDPOINT: APROBAR SOLICITUD (CAMBIA A APROBADA)
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Long id) {

        SolicitudEntity aprobada = solicitudService.aprobarSolicitud(id);

        notificacionService.crearNotificacion(
                aprobada.getEstudiante().getId(),
                "La solicitud del objeto '" + aprobada.getObjeto().getNombre() +
                        "' fue aprovada. Puedes acercarte al Centro de Objetos Perdidos para recoger tu pertenencia."

        );
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud aprobada correctamente");
        response.put("solicitud", aprobada);


        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable Long id) {

        SolicitudEntity rechazada = solicitudService.rechazarSolicitud(id);
        notificacionService.crearNotificacion(
                rechazada.getEstudiante().getId(),
                "Tu solicitud del objeto '" + rechazada.getObjeto().getNombre() + "' fue RECHAZADA"
        );
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Solicitud rechazada");
        response.put("solicitud", rechazada);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/historial")
    public ResponseEntity<List<SolicitudEntity>> listarHistorial() {
        return ResponseEntity.ok(solicitudService.listarHistorial());
    }
    @GetMapping("/aprobadas")
    public ResponseEntity<List<SolicitudEntity>> listarAprobadas() {
        List<SolicitudEntity> aprobadas = solicitudService.listarAprobadas();
        return ResponseEntity.ok(aprobadas);

    }

}