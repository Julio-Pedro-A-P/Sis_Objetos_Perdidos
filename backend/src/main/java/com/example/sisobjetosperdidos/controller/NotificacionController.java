package com.example.sisobjetosperdidos.controller;

import com.example.sisobjetosperdidos.entity.NotificacionEntity;
import com.example.sisobjetosperdidos.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificacionController {

    private final NotificacionService service;

    @GetMapping("/estudiante/{id}")
    public List<NotificacionEntity> obtenerNotificaciones(@PathVariable Long id) {
        return service.obtenerNoLeidas(id);
    }

    @PutMapping("/{id}/leido")
    public void marcarLeido(@PathVariable Long id) {
        service.marcarLeida(id);
    }
}
