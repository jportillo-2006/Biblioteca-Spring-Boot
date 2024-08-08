package com.jeffersonportillo.webapp.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Clientes")
public class Cliente {
    @Id
    private Long dpi;
    private String nombre;
    private String Apellido;
    private String telefono;
}
