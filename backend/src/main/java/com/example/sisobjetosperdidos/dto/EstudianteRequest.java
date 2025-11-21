package com.example.sisobjetosperdidos.dto;

import lombok.Data;

@Data
public class EstudianteRequest {
    private Long usuarioId;
    private String facultad;
    private String telefono;
}
