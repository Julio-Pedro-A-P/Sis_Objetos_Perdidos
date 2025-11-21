package com.example.sisobjetosperdidos.controller;

import com.example.sisobjetosperdidos.dto.ObjetoRequest;
import com.example.sisobjetosperdidos.dto.ObjetoResponse;
import com.example.sisobjetosperdidos.entity.ObjetoEntity;
import com.example.sisobjetosperdidos.enums.CategoriaObjeto;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import com.example.sisobjetosperdidos.repository.ObjetoRepository;
import com.example.sisobjetosperdidos.service.ObjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/objetos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ObjetoController {

    private final ObjetoService objetoService;

    @PostMapping("/registrar")
    public ResponseEntity<ObjetoResponse> registrarObjeto(
            @ModelAttribute ObjetoRequest request,
            @RequestParam("imagenes") List<MultipartFile> imagenes
    ) {
        return ResponseEntity.ok(objetoService.registrarObjeto(request, imagenes));
    }


    @PutMapping("/editar/{id}")
    public ResponseEntity<ObjetoResponse> editarObjeto(
            @PathVariable Long id,
            @ModelAttribute ObjetoRequest request,
            @RequestParam(value = "imagenes", required = false) List<MultipartFile> imagenes) {
        return ResponseEntity.ok(objetoService.editarObjeto(id, request, imagenes));
    }


    @GetMapping("/listar")
    public ResponseEntity<List<ObjetoResponse>> listar() {
        return ResponseEntity.ok(objetoService.listarObjetos());
    }

    // Endpoint para devolver las categorías del enum
    @GetMapping("/categorias")
    public ResponseEntity<List<String>> listarCategorias() {
        return ResponseEntity.ok(
                Arrays.stream(CategoriaObjeto.values())
                        .map(Enum::name)
                        .toList()
        );
    }
    private final ObjetoRepository objetoRepository;

    // LISTAR SOLO OBJETOS ACTIVOS
    @GetMapping("/activos")
    public ResponseEntity<List<ObjetoEntity>> listarActivos() {
        List<ObjetoEntity> activos = objetoRepository.findByEstado(EstadoObjeto.ACTIVO);
        return ResponseEntity.ok(activos);
    }

    //  REGISTRAR NUEVO OBJETO
    @PostMapping("/registrar1")
    public ResponseEntity<?> registrar(@RequestBody ObjetoEntity nuevo) {
        nuevo.setEstado(EstadoObjeto.ACTIVO);
        objetoRepository.save(nuevo);
        return ResponseEntity.ok(Map.of("mensaje", "Objeto registrado correctamente"));
    }
}
