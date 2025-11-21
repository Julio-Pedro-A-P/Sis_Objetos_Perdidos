package com.example.sisobjetosperdidos.repository;

import com.example.sisobjetosperdidos.entity.UsuarioEntity;
import com.example.sisobjetosperdidos.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreoInstitucional(String correoInstitucional);
    boolean existsByCorreoInstitucional(String correoInstitucional);
    boolean existsByCodigo(String codigo);
    List<UsuarioEntity> findByRol(Rol rol);
    long countByRol(Rol rol);



}
