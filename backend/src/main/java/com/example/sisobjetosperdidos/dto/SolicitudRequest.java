package com.example.sisobjetosperdidos.dto;


import lombok.Data;

import java.time.LocalDate;

@Data
public class SolicitudRequest {
    private Long objetoId;
    private Long estudianteId;
    private String lugarPerdida;
    private String colorMarca;
    private String detalles;
    private String fechaPerdida;
}

