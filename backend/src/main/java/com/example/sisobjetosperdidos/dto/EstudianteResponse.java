package com.example.sisobjetosperdidos.dto;

import lombok.Data;

@Data
public class EstudianteResponse {
    private Long id;
    private String facultad;
    private String telefono;

    // Datos del usuario
    private String correoInstitucional;
    private String nombres;
    private String codigo;
    private String rol;

}
