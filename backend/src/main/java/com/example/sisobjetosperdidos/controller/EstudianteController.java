package com.example.sisobjetosperdidos.controller;

import com.example.sisobjetosperdidos.dto.EstudianteRequest;
import com.example.sisobjetosperdidos.dto.EstudianteResponse;
import com.example.sisobjetosperdidos.repository.EstudianteRepository;
import com.example.sisobjetosperdidos.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PutMapping("/completar")
    public ResponseEntity<EstudianteResponse> completarDatos(
            @RequestBody EstudianteRequest request
    ) {

        Long usuarioId = request.getUsuarioId(); // Debe venir en el JSON

        EstudianteResponse response = estudianteService.completarDatos(usuarioId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/datos/{idUsuario}")
    public ResponseEntity<EstudianteResponse> obtenerPerfil(@PathVariable Long idUsuario) {
        EstudianteResponse response = estudianteService.obtenerPerfil(idUsuario);
        return ResponseEntity.ok(response);
    }
    private final EstudianteRepository estudianteRepository;
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        var estudiante = estudianteRepository.findById(id);

        if (estudiante.isPresent()) {
            return ResponseEntity.ok(estudiante.get());
        } else {
            return ResponseEntity.status(404).body("Estudiante no encontrado");
        }
    }




}
