package com.example.sisobjetosperdidos.service;

import com.example.sisobjetosperdidos.dto.ObjetoRequest;
import com.example.sisobjetosperdidos.dto.ObjetoResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjetoService {
    ObjetoResponse registrarObjeto(ObjetoRequest request, List<MultipartFile> imagenes);
    ObjetoResponse editarObjeto(Long id, ObjetoRequest request, List<MultipartFile> imagenes);
    List<ObjetoResponse> listarObjetos();
}
