package com.example.sisobjetosperdidos.dto;

import com.example.sisobjetosperdidos.enums.CategoriaObjeto;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjetoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String lugarEncontrado;
    private String fecha;
    private List<String> fotos;
    private EstadoObjeto estado;
    private CategoriaObjeto categoria;


}

