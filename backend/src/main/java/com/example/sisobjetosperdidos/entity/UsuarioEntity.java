package com.example.sisobjetosperdidos.entity;

import com.example.sisobjetosperdidos.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String correoInstitucional;

    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;

    @Enumerated(EnumType.STRING)
    private Rol rol = Rol.ESTUDIANTE;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private EstudianteEntity estudiante;
}
