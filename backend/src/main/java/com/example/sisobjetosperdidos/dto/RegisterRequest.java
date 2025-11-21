package com.example.sisobjetosperdidos.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String correoInstitucional;
    private String contrasena;
    private String nombres;
}
