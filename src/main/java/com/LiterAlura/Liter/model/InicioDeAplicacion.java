package com.LiterAlura.Liter.model;

import com.LiterAlura.Liter.Repository.LibroRepository;
import com.LiterAlura.Liter.service.ConsumoApi;
import com.LiterAlura.Liter.service.ConvertirDatos;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class InicioDeAplicacion {

    //variables
    private Scanner in = new Scanner(System.in);

    private final String URL_BASE ="https://gutendex.com/books/?search=";

    private LibroRepository libroRepository;

    private ConvertirDatos convertirDatos = new ConvertirDatos();

    private ConsumoApi consumoApi = new ConsumoApi();

    private List<DatosLibro> libros = new ArrayList<>();

    private Autor autor = new Autor();

    public InicioDeAplicacion(LibroRepository libroRepository){
        this.libroRepository = libroRepository;
    }

    //muestra el menu.
    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("---------------------------------");
            System.out.println("Ingrese la opcion: " + '\n'
                    + "1) Buscar libro por titulo" + '\n'
                    + "2) Listar libros registrados" + '\n'
                    + "3) Listar autores registrados" + '\n'
                    + "4) Listar autories vivos por un determinado año" + '\n'
                    + "5) Listar libros por idioma" + '\n'
                    + "0) Salir");
            System.out.println("---------------------------------");
            //ingreso de datos usuario
            opcion = in.nextInt();
            in.nextLine();
            
            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    buscarAutoresVivos();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private void buscarLibroPorIdioma() {
        System.out.println("Ingrese la sigla del idioma a buscar" + '\n'
                +" en - Ingles" + '\n'
                +" es - Español" + '\n'
                +" fr - Frances" + '\n'
                +" pr - Protugues");
        String idioma = in.nextLine();
        List<Libro> libros = libroRepository.buscarPorIdioma(idioma);
        try {
            libros.stream().forEach(System.out::println);
        }catch (NullPointerException e){
            System.out.println("ERROR DE BUSQUEDA");
        }
    }

    private void buscarAutoresVivos() {
        System.out.println("Ingrese el año de busqueda -FORMATO YYYY-");
        String anioBusqueda = in.nextLine();
        //traemos busqueda
        List<Autor> autoresVivos = libroRepository.buscarAutoresVivos(anioBusqueda);
        try {
            autoresVivos.stream().forEach(System.out::println);
        }catch (NullPointerException e){
            System.out.println("ERROR DE BUSQUEDA");
        }

    }

    private void mostrarAutoresRegistrados() {
        //traemos todos los autores registrados
        System.out.println("---------Autores Registrados---------");
        List<Autor> autores = libroRepository.obtenerAutores();
        autores.stream().forEach(System.out::println);
    }

    private void mostrarLibrosRegistrados() {
        //traemos todos los libros
        System.out.println("---------Libros Registrados---------");
        List<Libro> libros = libroRepository.findAll();

        try {
            libros.stream().forEach(System.out::println);
        }catch (NullPointerException e){
            System.out.println("libro sin autor");
        }
    }

    @Transactional
    private void buscarLibroWeb() {

        //pedimos nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String nombre = in.nextLine();

        //obtenemos el json
        var json = consumoApi.obtenerDatos(URL_BASE + nombre.replace(" ", "%20"));
        //realizamos conversion.
        libros = convertirDatos.convertirLista(json, DatosLibro.class);

        //traemos siempre el primer resultado
        Optional<DatosLibro> datosLibro = libros.stream()
                .filter(l-> l.titulo().toLowerCase().contains(nombre.toLowerCase()))
                .findFirst();

        if(datosLibro.isPresent()){
            DatosLibro libroEncontrado = datosLibro.get();
            System.out.println(libroEncontrado);
            try{
                //creamos el autor
                autor = new Autor(libroEncontrado.autor().get(0));
            }catch (IndexOutOfBoundsException e){
                System.out.println("NO CONTIENE AUTOR");
                autor = null;
            }
            //creamos el libro
            Libro libro = new Libro(libroEncontrado);
            libro.setAutor(autor);

            libroRepository.save(libro);
        }else {
            System.out.println("----------------------------------------------");
            System.out.println("| NO SE ENCONTRO EL LIBRO, INTENTE NUEVAMENTE |");
            System.out.println("----------------------------------------------");
        }

    }
}

