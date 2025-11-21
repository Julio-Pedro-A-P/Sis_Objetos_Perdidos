package com.example.sisobjetosperdidos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class JwtResponse {
    private String token;
    private String rol;
    private Long idUsuario;
    private String nombreUsuario;
}
