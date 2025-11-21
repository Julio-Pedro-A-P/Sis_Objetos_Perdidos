package com.example.sisobjetosperdidos.dto;

import com.example.sisobjetosperdidos.enums.CategoriaObjeto;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ObjetoRequest {
    private String nombre;
    private String descripcion;
    private String lugarEncontrado;
    private LocalDate fecha;
    private CategoriaObjeto categoria;

}
