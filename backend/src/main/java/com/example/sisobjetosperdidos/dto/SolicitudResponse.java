package com.example.sisobjetosperdidos.dto;

import com.example.sisobjetosperdidos.entity.SolicitudEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitudResponse {
    private String mensaje;
    private SolicitudEntity solicitud;
}
