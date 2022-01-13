package br.com.josias.animes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class AnimesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimesApplication.class, args);
	}

	@GetMapping
	public String home() {
		return "<h1 align='center'>API Animes</h1>"
				+ "<a align='center' href='https://crud-animes.herokuapp.com/swagger-ui.html'>Endpoints Swagger UI</a>";
	}
	
}
