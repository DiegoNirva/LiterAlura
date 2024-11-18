package com.LiterAlura.Liter.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;


    private String idioma;
    private int numDeDescargas;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idioma().getFirst();
        this.numDeDescargas = datosLibro.numDeDescargas();
    }


    @Override
    public String toString() {
        return "------------Libro----------" + '\n' +
                "Autor:" + autor.getNombre() + '\n' +
                "Titulo:" + titulo + '\n' +
                "Idioma:" + idioma + '\n' +
                "Num. De Descargas:" + numDeDescargas + '\n' +
                "--------------------------";
    }
}
