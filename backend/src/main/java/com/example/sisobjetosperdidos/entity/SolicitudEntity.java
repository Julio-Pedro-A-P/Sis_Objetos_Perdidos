package com.example.sisobjetosperdidos.entity;


import com.example.sisobjetosperdidos.enums.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "solicitudes")
public class SolicitudEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "objeto_id")
    private ObjetoEntity objeto;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private EstudianteEntity estudiante;

    private String lugarPerdida;
    private String colorMarca;
    private String detalles;
    @Column(name = "fecha_perdida")
    private LocalDate fechaPerdida;


    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

}
