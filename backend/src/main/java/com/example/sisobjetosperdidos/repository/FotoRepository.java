package com.example.sisobjetosperdidos.repository;

import com.example.sisobjetosperdidos.entity.FotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<FotoEntity, Long> {
    List<FotoEntity> findByObjetoId(Long objetoId);
}