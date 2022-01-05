package br.com.josias.animes.controller;

import java.util.List;

//import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.josias.animes.model.Anime;
import br.com.josias.animes.requests.AnimeDTO;
import br.com.josias.animes.service.AnimeService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
//@Tag(name="CRUD Animes")
@RequestMapping("/api/animes/user")
public class AnimeController {

	private AnimeService animeService;
	
	@Autowired
	public AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}

	@GetMapping
//	@Operation(summary = "List all animes paginated", description = "The default size is 20, use the parameter size to change the default value",
//	tags = {"anime"})
	public ResponseEntity<Page<Anime>> listAllAnimesPageable(/*@ParameterObject*/ Pageable pageable) {
		return ResponseEntity.ok(animeService.listAllPageable(pageable));
	}
	
	@GetMapping("/all")
//	@Operation(summary = "List all animes no paginated", description = "list of all animes",tags = {"anime"})
	public ResponseEntity<List<Anime>> listAllAnimesNonPageable() {
		return ResponseEntity.ok(animeService.listAllNonPageable());
	}
	
	@GetMapping("/find")
//	@Operation(summary = "Find animes by name with request param", description = "find animes by name",tags = {"anime"})
	public ResponseEntity<List<Anime>> findAnimeByName(@RequestParam String name) {
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Anime> findAnimeById(@PathVariable Long id) {
		return ResponseEntity.ok(animeService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Anime> createAnime(@RequestBody AnimeDTO animeDTO) {
		return new ResponseEntity<>(animeService.save(animeDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Anime> replaceAnime(@PathVariable Long id,@RequestBody AnimeDTO animeDTO) {
		return ResponseEntity.ok(animeService.replace(id,animeDTO));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeAnime(@PathVariable Long id) {
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
