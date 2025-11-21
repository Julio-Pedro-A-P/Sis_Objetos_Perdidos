package com.example.sisobjetosperdidos.entity;

import com.example.sisobjetosperdidos.enums.CategoriaObjeto;
import com.example.sisobjetosperdidos.enums.EstadoObjeto;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "objetos")
@Data
public class ObjetoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @Column(length = 500)
    private String descripcion;
    private String lugarEncontrado;
    private LocalDate fecha;
    private String fotoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaObjeto categoria;



    @OneToMany(mappedBy = "objeto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<FotoEntity> fotos;



    @Enumerated(EnumType.STRING)
    private EstadoObjeto estado;

}
