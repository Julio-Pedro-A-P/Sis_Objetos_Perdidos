package com.example.sisobjetosperdidos.repository;

import com.example.sisobjetosperdidos.entity.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Long> {
    Optional<EstudianteEntity> findByUsuarioId(Long usuarioId);

}
