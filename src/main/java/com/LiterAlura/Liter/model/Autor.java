package com.LiterAlura.Liter.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String fechaNacimiento;
    private String fechaFallecimiento;

    public Autor() {
    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }


    @Override
    public String toString() {
        return "-----------------Autor---------------" + '\n' +
                "Nombre:" + nombre + '\n' +
                "Fecha de nacimiento:" + fechaNacimiento + '\n' +
                "Fecha de fallecimiento:" + fechaFallecimiento + '\n' +
                "------------------------------------";
    }
}
