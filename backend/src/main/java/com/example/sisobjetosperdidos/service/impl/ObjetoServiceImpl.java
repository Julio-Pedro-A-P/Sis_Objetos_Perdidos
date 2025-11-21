package com.example.sisobjetosperdidos.service.impl;

import com.example.sisobjetosperdidos.dto.ObjetoRequest;
import com.example.sisobjetosperdidos.dto.ObjetoResponse;
import com.example.sisobjetosperdidos.entity.FotoEntity;
import com.example.sisobjetosperdidos.entity.ObjetoEntity;
import com.example.sisobjetosperdidos.entity.SolicitudEntity;
import com.example.sisobjetosperdidos.enums.CategoriaObjeto;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import com.example.sisobjetosperdidos.enums.EstadoSolicitud;
import com.example.sisobjetosperdidos.repository.FotoRepository;
import com.example.sisobjetosperdidos.repository.ObjetoRepository;
import com.example.sisobjetosperdidos.service.ObjetoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjetoServiceImpl implements ObjetoService {

    private final ObjetoRepository objetoRepository;
    private final FotoRepository fotoRepository;

    @Override
    public ObjetoResponse registrarObjeto(ObjetoRequest request, List<MultipartFile> imagenes) {
        ObjetoEntity objeto = new ObjetoEntity();
        objeto.setNombre(request.getNombre());
        objeto.setDescripcion(request.getDescripcion());
        objeto.setLugarEncontrado(request.getLugarEncontrado());
        objeto.setFecha(request.getFecha());
        objeto.setCategoria(request.getCategoria());
        objeto.setEstado(EstadoObjeto.ACTIVO);

        objetoRepository.save(objeto);

        guardarImagenes(imagenes, objeto);
        return mapToResponse(objeto);
    }

    @Override
    public ObjetoResponse editarObjeto(Long id, ObjetoRequest request, List<MultipartFile> imagenes) {
        ObjetoEntity objeto = objetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Objeto no encontrado con id: " + id));

        objeto.setNombre(request.getNombre());
        objeto.setDescripcion(request.getDescripcion());
        objeto.setLugarEncontrado(request.getLugarEncontrado());
        objeto.setFecha(request.getFecha());
        if (request.getCategoria() != null) {
            try {
                objeto.setCategoria(
                        CategoriaObjeto.valueOf(request.getCategoria().toString().toUpperCase())
                );
            } catch (IllegalArgumentException e) {
                System.out.println("Categoría inválida recibida: " + request.getCategoria());
            }

        }



        objetoRepository.save(objeto);

        if (imagenes != null && !imagenes.isEmpty()) {
            guardarImagenes(imagenes, objeto);
        }

        return mapToResponse(objeto);
    }



    @Override
    public List<ObjetoResponse> listarObjetos() {
        return objetoRepository.findByEstado(EstadoObjeto.ACTIVO)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private void guardarImagenes(List<MultipartFile> imagenes, ObjetoEntity objeto) {
        if (imagenes == null || imagenes.isEmpty()) return;

        Path uploadsDir = Paths.get("uploads");
        try {
            if (!Files.exists(uploadsDir)) Files.createDirectories(uploadsDir);

            for (MultipartFile img : imagenes) {
                String nombreArchivo = UUID.randomUUID() + "_" + img.getOriginalFilename();
                Path destino = uploadsDir.resolve(nombreArchivo);
                Files.copy(img.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

                FotoEntity foto = new FotoEntity();
                foto.setUrl(nombreArchivo);
                foto.setObjeto(objeto);
                fotoRepository.save(foto);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar imágenes", e);
        }
    }

    private ObjetoResponse mapToResponse(ObjetoEntity objeto) {
        List<String> fotos = fotoRepository.findByObjetoId(objeto.getId())
                .stream().map(FotoEntity::getUrl).toList();

        return new ObjetoResponse(
                objeto.getId(),
                objeto.getNombre(),
                objeto.getDescripcion(),
                objeto.getLugarEncontrado(),
                objeto.getFecha().toString(),
                fotos,
                objeto.getEstado(),
                objeto.getCategoria()
        );
    }


}
