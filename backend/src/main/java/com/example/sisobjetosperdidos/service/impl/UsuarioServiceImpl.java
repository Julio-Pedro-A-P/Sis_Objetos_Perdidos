package com.example.sisobjetosperdidos.service.impl;

import com.example.sisobjetosperdidos.entity.UsuarioEntity;
import com.example.sisobjetosperdidos.enums.Rol;
import com.example.sisobjetosperdidos.repository.UsuarioRepository;
import com.example.sisobjetosperdidos.service.UsuarioService;
import com.example.sisobjetosperdidos.util.CodigoGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioEntity registrarUsuario(UsuarioEntity usuario) {

        // 🔹 Generar código numérico único (solo si es estudiante)
        if (usuario.getRol().name().equalsIgnoreCase("ESTUDIANTE")) {
            String codigoGenerado;
            do {
                codigoGenerado = CodigoGenerator.generarCodigoNumerico(8);
            } while (usuarioRepository.existsByCodigo(codigoGenerado));

            usuario.setCodigo(codigoGenerado);
        }

        // Guardar usuario
        return usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioEntity buscarPorCodigo(String codigo) {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UsuarioEntity buscarPorCorreoInstitucional(String correo) {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getCorreoInstitucional().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<UsuarioEntity> listarSoloEstudiantes() {
        return usuarioRepository.findByRol(Rol.ESTUDIANTE);
    }
    @GetMapping("/total")
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public long contarEstudiantes() {
        return usuarioRepository.countByRol(Rol.ESTUDIANTE);
    }



}
