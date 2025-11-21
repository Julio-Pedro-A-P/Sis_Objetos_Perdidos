package com.example.sisobjetosperdidos.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notificaciones")
public class NotificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long estudianteId;

    private String mensaje;

    private Boolean leido = false;

    private LocalDateTime fecha = LocalDateTime.now();
}
