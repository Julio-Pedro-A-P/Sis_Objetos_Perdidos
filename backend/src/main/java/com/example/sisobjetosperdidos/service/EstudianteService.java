package com.example.sisobjetosperdidos.service;

import com.example.sisobjetosperdidos.dto.EstudianteRequest;
import com.example.sisobjetosperdidos.dto.EstudianteResponse;

public interface EstudianteService {


        EstudianteResponse obtenerPerfil(Long usuarioId);

        EstudianteResponse completarDatos(Long usuarioId, EstudianteRequest request);




}
