package br.com.josias.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.josias.apirest.model.Anime;
import br.com.josias.apirest.requests.AnimePostRequestBody;
import br.com.josias.apirest.requests.AnimePutRequestBody;
import br.com.josias.apirest.service.AnimeService;

@RestController
@RequestMapping("/api/v1/user/animes")
public class AnimeController {

	private AnimeService animeService;
	
	@Autowired
	public AnimeController(AnimeService animeService) {
		this.animeService = animeService;
	}

	@GetMapping
	public ResponseEntity<List<Anime>> allAnimes(Anime anime) {
		return ResponseEntity.ok(animeService.listAll());
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Anime>> findAnimeByName(@RequestParam String name) {
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Anime> findAnimeById(@PathVariable Long id) {
		return ResponseEntity.ok(animeService.findById(id));
	}
	
	@PostMapping
	public ResponseEntity<Anime> createAnime(@RequestBody AnimePostRequestBody animePostRequestBody) {
		return new ResponseEntity<>(animeService.save(animePostRequestBody),HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<Void> replaceAnime(@RequestBody AnimePutRequestBody animePutRequestBody) {
		animeService.replace(animePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeAnime(@PathVariable Long id) {
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
