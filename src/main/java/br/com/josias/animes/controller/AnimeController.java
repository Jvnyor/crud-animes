package br.com.josias.animes.controller;

import java.util.List;

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

import br.com.josias.animes.model.Anime;
import br.com.josias.animes.requests.AnimeDTO;
import br.com.josias.animes.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Animes API")
@RequestMapping("/api/animes")
@CrossOrigin(origins="*")
public class AnimeController {

	@Autowired
	private AnimeService animeService;

	@GetMapping
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Anime List Paginated Does Exist in The Database")
	})
	@Operation(summary = "List all animes paginated", description = "The default size is 20, use the parameter size to change the default value",
	tags = {"anime"})
	public ResponseEntity<Page<Anime>> listAllPageable(@ParameterObject Pageable pageable) {
		return ResponseEntity.ok(animeService.listAllPageable(pageable));
	}
	
	
	@GetMapping("/all")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Animes List Does Exist in The Database")
	})
	@Operation(summary = "List all animes no paginated", description = "paginated list",tags = {"anime"})
	public ResponseEntity<List<Anime>> listAllNonPageable() {
		return ResponseEntity.ok(animeService.listAllNonPageable());
	}
	
	@GetMapping("/find")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Anime Does Exist in The Database")
	})
	@Operation(summary = "Find animes with request param name", description = "animes by name",tags = {"anime"})
	public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	@GetMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Anime Does Exist in The Database")
	})
	@Operation(summary = "Find animes with path variable id",description = "anime by id",tags = {"anime"})
	public ResponseEntity<Anime> findById(@PathVariable Long id) {
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}
	
	@PostMapping
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Anime body is not valid")
	})
	@Operation(summary = "Create animes with request body",description = "animes saving",tags = {"anime"})
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimeDTO animeDTO) {
		return new ResponseEntity<>(animeService.save(animeDTO),HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Anime Does Exist in The Database")
	})
	@Operation(summary = "Replace animes with request body using path variable id",description = "animes replacing",tags = {"anime"})
	public ResponseEntity<Anime> replace(@PathVariable Long id,@RequestBody AnimeDTO animeDTO) {
		return ResponseEntity.ok(animeService.replace(id,animeDTO));
	}
	
	@DeleteMapping("/{id}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Successful Operation"),
			@ApiResponse(responseCode = "400", description = "When Anime Does Exist in The Database")
	})
	@Operation(summary = "Removes anime with path variable id",description = "animes deletions",tags = {"anime"})
	public ResponseEntity<Void> remove(@PathVariable Long id) {
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
