package com.example.sisobjetosperdidos.repository;

import com.example.sisobjetosperdidos.entity.NotificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<NotificacionEntity, Long> {

    List<NotificacionEntity> findByEstudianteIdAndLeidoFalse(Long estudianteId);
}
