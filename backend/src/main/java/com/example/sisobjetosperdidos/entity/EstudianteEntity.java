package com.example.sisobjetosperdidos.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "estudiantes")
public class EstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String facultad;
    private String telefono;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    @JsonIgnore
    private UsuarioEntity usuario;
    @Transient
    public String getNombres() {
        return usuario != null ? usuario.getNombres() : null;
    }

    @Transient
    public String getCodigo() {
        return usuario != null ? usuario.getCodigo() : null;
    }


}
