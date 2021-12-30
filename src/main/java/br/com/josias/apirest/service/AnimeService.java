package br.com.josias.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.Anime;
import br.com.josias.apirest.repository.AnimeRepository;
import br.com.josias.apirest.requests.AnimeDTO;

@Service
public class AnimeService {

	private AnimeRepository animeRepository;
	
	@Autowired
	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public Page<Anime> listAllPageable(Pageable pageable) {
		return animeRepository.findAll(pageable);
	}
	
	public List<Anime> listAllNonPageable() {
		return animeRepository.findAll();
	}
	
	public Anime findById(Long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}
	
	public List<Anime> findByName(String name) {
		return animeRepository.findByName(name);
	}
	
	public Anime save(AnimeDTO animeDTO) {
		return animeRepository.save(Anime.builder().name(animeDTO.getName()).build());
	}
	
	public Anime replace(Long id,AnimeDTO animeDTO) {
		Anime savedAnime = animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		return animeRepository.save(Anime.builder().id(savedAnime.getId()).name(animeDTO.getName()).build());
	}
	
	public void delete(Long id) {
		animeRepository.delete(findById(id));
	}
}
