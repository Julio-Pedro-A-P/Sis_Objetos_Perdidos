package com.example.sisobjetosperdidos.service;

import com.example.sisobjetosperdidos.dto.SolicitudRequest;
import com.example.sisobjetosperdidos.dto.SolicitudResponse;
import com.example.sisobjetosperdidos.entity.SolicitudEntity;
import com.example.sisobjetosperdidos.enums.EstadoSolicitud;

import java.util.List;

public interface SolicitudService  {
    List<SolicitudEntity> listarAprobadas();

    List<SolicitudEntity> listarHistorial();

    SolicitudEntity crearSolicitud(SolicitudRequest request);
    List<SolicitudEntity> listarPorEstudiante(Long estudianteId);

    List<SolicitudEntity> listarPendientes();

    SolicitudEntity aprobarSolicitud(Long id);

    SolicitudEntity rechazarSolicitud(Long id);

}

