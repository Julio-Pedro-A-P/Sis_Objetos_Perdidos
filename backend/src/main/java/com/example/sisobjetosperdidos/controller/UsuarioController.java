package com.example.sisobjetosperdidos.controller;

import com.example.sisobjetosperdidos.entity.UsuarioEntity;
import com.example.sisobjetosperdidos.repository.UsuarioRepository;
import com.example.sisobjetosperdidos.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @Autowired
    private UsuarioService usuarioService;
    private UsuarioRepository usuarioRepository;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody UsuarioEntity usuario) {
        UsuarioEntity nuevo = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }
    @GetMapping("/estudiantes")
    public ResponseEntity<List<UsuarioEntity>> listarSoloEstudiantes() {
        return ResponseEntity.ok(usuarioService.listarSoloEstudiantes());
    }
    @GetMapping("/total")
    public long contarUsuariosEstudiantes() {
        return usuarioService.contarEstudiantes();
    }




}
