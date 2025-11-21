package com.example.sisobjetosperdidos.repository;

import com.example.sisobjetosperdidos.entity.SolicitudEntity;
import com.example.sisobjetosperdidos.enums.EstadoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<SolicitudEntity, Long> {

    boolean existsByEstudianteIdAndObjetoId(Long estudianteId, Long objetoId);

    List<SolicitudEntity> findByEstudianteId(Long estudianteId);

    List<SolicitudEntity> findByEstado(EstadoSolicitud estado);

    List<SolicitudEntity> findByEstadoNot(EstadoSolicitud estado);

}
