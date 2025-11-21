package com.example.sisobjetosperdidos.service;

import com.example.sisobjetosperdidos.entity.NotificacionEntity;
import java.util.List;

public interface NotificacionService {
    void crearNotificacion(Long estudianteId, String mensaje);
    List<NotificacionEntity> obtenerNoLeidas(Long estudianteId);
    void marcarLeida(Long id);
}
