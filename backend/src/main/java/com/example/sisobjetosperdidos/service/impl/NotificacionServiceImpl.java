package com.example.sisobjetosperdidos.service.impl;

import com.example.sisobjetosperdidos.entity.NotificacionEntity;
import com.example.sisobjetosperdidos.repository.NotificacionRepository;
import com.example.sisobjetosperdidos.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository repo;

    @Override
    public void crearNotificacion(Long estudianteId, String mensaje) {
        NotificacionEntity n = new NotificacionEntity();
        n.setEstudianteId(estudianteId);
        n.setMensaje(mensaje);
        repo.save(n);
    }

    @Override
    public List<NotificacionEntity> obtenerNoLeidas(Long estudianteId) {
        return repo.findByEstudianteIdAndLeidoFalse(estudianteId);
    }

    @Override
    public void marcarLeida(Long id) {
        NotificacionEntity n = repo.findById(id).orElseThrow();
        n.setLeido(true);
        repo.save(n);
    }
}
