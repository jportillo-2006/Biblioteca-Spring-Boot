package com.jeffersonportillo.webapp.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name = "Empleados")
public class Empleado {
    @Id
    private Long id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String dpi;
}
