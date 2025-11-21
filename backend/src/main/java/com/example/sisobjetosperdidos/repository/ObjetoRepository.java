package com.example.sisobjetosperdidos.repository;

import com.example.sisobjetosperdidos.entity.ObjetoEntity;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetoRepository extends JpaRepository<ObjetoEntity, Long> {
    List<ObjetoEntity> findByEstado(EstadoObjeto estado);


}
