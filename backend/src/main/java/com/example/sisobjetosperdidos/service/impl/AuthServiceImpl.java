package com.example.sisobjetosperdidos.service.impl;

import com.example.sisobjetosperdidos.dto.JwtResponse;
import com.example.sisobjetosperdidos.dto.LoginRequest;
import com.example.sisobjetosperdidos.dto.RegisterRequest;
import com.example.sisobjetosperdidos.entity.EstudianteEntity;
import com.example.sisobjetosperdidos.entity.UsuarioEntity;
import com.example.sisobjetosperdidos.enums.Rol;
import com.example.sisobjetosperdidos.repository.EstudianteRepository;
import com.example.sisobjetosperdidos.repository.UsuarioRepository;
import com.example.sisobjetosperdidos.service.AuthService;
import com.example.sisobjetosperdidos.util.CodigoGenerator;
import com.example.sisobjetosperdidos.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final EstudianteRepository estudianteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse login(LoginRequest request) {

        UsuarioEntity usuario = usuarioRepository.findByCorreoInstitucional(request.getCorreoInstitucional())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtUtil.generarToken(usuario);

        return JwtResponse.builder()
                .token(token)
                .rol(usuario.getRol().name())
                .idUsuario(usuario.getId())
                .nombreUsuario(usuario.getNombres())
                .build();
    }
    @Override
    public String register(RegisterRequest request) {

        if (usuarioRepository.existsByCorreoInstitucional(request.getCorreoInstitucional())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Generar código único de usuario
        String codigoGenerado;
        do {
            codigoGenerado = CodigoGenerator.generarCodigoNumerico(8);
        } while (usuarioRepository.existsByCodigo(codigoGenerado));

        // Crear usuario
        UsuarioEntity nuevoUsuario = UsuarioEntity.builder()
                .correoInstitucional(request.getCorreoInstitucional())
                .contrasena(passwordEncoder.encode(request.getContrasena()))
                .nombres(request.getNombres())
                .codigo(codigoGenerado)
                .rol(Rol.ESTUDIANTE)
                .build();

        usuarioRepository.save(nuevoUsuario);

        // Crear estudiante asociado
        EstudianteEntity estudiante = EstudianteEntity.builder()
                .usuario(nuevoUsuario)
                .facultad(null)
                .telefono(null)
                .build();

        estudianteRepository.save(estudiante);

        return "Usuario registrado correctamente";
    }
}
