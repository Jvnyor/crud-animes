package com.Jvnyor.animes.controller;

import javax.validation.Valid;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Jvnyor.animes.model.Anime;
import com.Jvnyor.animes.requests.AnimeDTO;
import com.Jvnyor.animes.service.AnimeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Animes API")
@RequestMapping("/api/animes")
@CrossOrigin(origins="*")
public class AnimeController {

	@Autowired
	private AnimeService animeService;

	@GetMapping
	@Operation(summary = "List all animes paginated", description = "The default size is 20, use the parameter size to change the default value",
	tags = {"anime"})
	public ResponseEntity<Page<Anime>> listAllPageable(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(animeService.listAllPageable(pageable));
	}
	
	@GetMapping("/find")
	@Operation(summary = "Find animes with request param name", description = "animes by name",tags = {"anime"})
	public ResponseEntity<Page<Anime>> findByName(@RequestParam String name, Pageable pageable) {
		return ResponseEntity.ok(animeService.findByName(name, pageable));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Find animes with path variable id",description = "anime by id",tags = {"anime"})
	public ResponseEntity<Anime> findById(@PathVariable Long id) {
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}
	
	@PostMapping
	@Operation(summary = "Create animes with request body",description = "animes saving",tags = {"anime"})
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimeDTO animeDTO) {
		return new ResponseEntity<>(animeService.save(animeDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Replace animes with request body using path variable id",description = "animes replacing",tags = {"anime"})
	public ResponseEntity<Anime> replace(@PathVariable Long id,@RequestBody AnimeDTO animeDTO) {
		return ResponseEntity.ok(animeService.replace(id, animeDTO));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Removes anime with path variable id",description = "animes deletions",tags = {"anime"})
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
