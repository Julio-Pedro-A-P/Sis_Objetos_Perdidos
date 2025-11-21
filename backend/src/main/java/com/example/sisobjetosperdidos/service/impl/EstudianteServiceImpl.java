package com.example.sisobjetosperdidos.service.impl;
import com.example.sisobjetosperdidos.dto.EstudianteRequest;
import com.example.sisobjetosperdidos.dto.EstudianteResponse;
import com.example.sisobjetosperdidos.mapper.EstudianteMapper;
import com.example.sisobjetosperdidos.repository.EstudianteRepository;
import com.example.sisobjetosperdidos.repository.UsuarioRepository;
import com.example.sisobjetosperdidos.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {


    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstudianteMapper mapper;

    @Override
    public EstudianteResponse obtenerPerfil(Long usuarioId) {

        var estudiante = estudianteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        var response = mapper.toResponse(estudiante);

        response.setCodigo(estudiante.getUsuario().getCodigo());

        return response;
    }

    @Override
    public EstudianteResponse completarDatos(Long usuarioId, EstudianteRequest request) {

        var estudiante = estudianteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Actualizar datos
        estudiante.setFacultad(request.getFacultad());
        estudiante.setTelefono(request.getTelefono());



        estudianteRepository.save(estudiante);

        return mapper.toResponse(estudiante);
    }



}
