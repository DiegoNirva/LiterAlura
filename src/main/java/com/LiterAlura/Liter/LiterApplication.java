package com.LiterAlura.Liter;

import com.LiterAlura.Liter.Repository.LibroRepository;
import com.LiterAlura.Liter.model.InicioDeAplicacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterApplication.class, args);


	}


	@Override
	public void run(String... args) throws Exception {

		InicioDeAplicacion inicioDeAplicacion = new InicioDeAplicacion(libroRepository);

		inicioDeAplicacion.muestraMenu();

	}
}
