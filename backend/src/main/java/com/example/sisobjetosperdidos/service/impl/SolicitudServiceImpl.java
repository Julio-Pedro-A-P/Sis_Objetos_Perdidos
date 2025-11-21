package com.example.sisobjetosperdidos.service.impl;

import com.example.sisobjetosperdidos.dto.SolicitudRequest;
import com.example.sisobjetosperdidos.entity.ObjetoEntity;
import com.example.sisobjetosperdidos.entity.SolicitudEntity;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import com.example.sisobjetosperdidos.enums.EstadoSolicitud;
import com.example.sisobjetosperdidos.exception.SolicitudDuplicadaException;
import com.example.sisobjetosperdidos.repository.EstudianteRepository;
import com.example.sisobjetosperdidos.repository.ObjetoRepository;
import com.example.sisobjetosperdidos.repository.SolicitudRepository;
import com.example.sisobjetosperdidos.service.SolicitudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final EstudianteRepository estudianteRepository;
    private final ObjetoRepository objetoRepository;


    @Transactional
    @Override
    public SolicitudEntity crearSolicitud(SolicitudRequest request) {

        // VALIDAR si el estudiante ya envió solicitud para ese objeto
        if (solicitudRepository.existsByEstudianteIdAndObjetoId(
                request.getEstudianteId(), request.getObjetoId())) {
            throw new SolicitudDuplicadaException("Ya has enviado una solicitud para este objeto.");
        }

        var estudiante = estudianteRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        var objeto = objetoRepository.findById(request.getObjetoId())
                .orElseThrow(() -> new RuntimeException("Objeto no encontrado"));

        LocalDate fecha = null;
        if (request.getFechaPerdida() != null && !request.getFechaPerdida().isEmpty()) {
            fecha = LocalDate.parse(request.getFechaPerdida());
        }

        SolicitudEntity solicitud = SolicitudEntity.builder()
                .estudiante(estudiante)
                .objeto(objeto)
                .lugarPerdida(request.getLugarPerdida())
                .colorMarca(request.getColorMarca())
                .detalles(request.getDetalles())
                .fechaPerdida(fecha)
                .estado(EstadoSolicitud.PENDIENTE)
                .build();

        return solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudEntity> listarPorEstudiante(Long estudianteId) {
        return solicitudRepository.findByEstudianteId(estudianteId);
    }

    @Override
    public List<SolicitudEntity> listarPendientes() {
        return solicitudRepository.findByEstado(EstadoSolicitud.PENDIENTE);
    }


    @Override
    public SolicitudEntity aprobarSolicitud(Long id) {

        var solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // CAMBIAR ESTADO DE LA SOLICITUD
        solicitud.setEstado(EstadoSolicitud.APROBADA);

        // MARCAR OBJETO COMO ENTREGADO
        ObjetoEntity objeto = solicitud.getObjeto();
        if (objeto != null) {
            objeto.setEstado(EstadoObjeto.ENTREGADO);
            objetoRepository.save(objeto);
        }

        return solicitudRepository.save(solicitud);
    }



    @Override
    public SolicitudEntity rechazarSolicitud(Long id) {

        var solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.RECHAZADA);
        return solicitudRepository.save(solicitud);
    }
    @Override
    public List<SolicitudEntity> listarHistorial() {
        return solicitudRepository.findByEstadoNot(EstadoSolicitud.PENDIENTE);
    }
    @Override
    public List<SolicitudEntity> listarAprobadas() {
        return solicitudRepository.findByEstado(EstadoSolicitud.APROBADA);
    }



}
