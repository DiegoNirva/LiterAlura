package com.LiterAlura.Liter.Repository;

import com.LiterAlura.Liter.model.Autor;
import com.LiterAlura.Liter.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT DISTINCT l.autor FROM Libro l WHERE l.autor IS NOT NULL")
    List<Autor> obtenerAutores();


    @Query("SELECT DISTINCT a FROM Libro l JOIN l.autor a WHERE a.fechaNacimiento <= :anioBusqueda AND a.fechaFallecimiento >= :anioBusqueda")
    List<Autor> buscarAutoresVivos(String anioBusqueda);

    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma AND l.autor IS NOT NULL")
    List<Libro> buscarPorIdioma(String idioma);
}
