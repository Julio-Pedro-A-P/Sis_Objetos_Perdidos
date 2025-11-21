package com.example.sisobjetosperdidos.controller;

import com.example.sisobjetosperdidos.dto.LoginRequest;
import com.example.sisobjetosperdidos.dto.RegisterRequest;
import com.example.sisobjetosperdidos.repository.UsuarioRepository;
import com.example.sisobjetosperdidos.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    //  Dependencias inyectadas con Lombok
    private final AuthService authService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterRequest request) {
        String mensaje = authService.register(request);
        return ResponseEntity.ok(Map.of("mensaje", mensaje));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        System.out.println("Recibido correo: " + request.getCorreoInstitucional());
        System.out.println("Recibido contraseña: " + request.getContrasena());

        var usuario = usuarioRepository.findByCorreoInstitucional(request.getCorreoInstitucional())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("mensaje", "Contraseña incorrecta"));
        }

        //  Generar token JWT
        var jwtResponse = authService.login(request);

        return ResponseEntity.ok(Map.of(
                "mensaje", "Inicio de sesión exitoso",
                "rol", usuario.getRol().toString(),
                "token", jwtResponse.getToken(),
                "id", usuario.getId(),
                "nombre", usuario.getNombres(),
                "correo", usuario.getCorreoInstitucional()
        ));
    }

}
