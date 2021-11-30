package br.com.josias.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.Anime;
import br.com.josias.apirest.repository.AnimeRepository;
import br.com.josias.apirest.requests.AnimePostRequestBody;
import br.com.josias.apirest.requests.AnimePutRequestBody;

@Service
public class AnimeService {

	private AnimeRepository animeRepository;
	
	@Autowired
	public AnimeService(AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public List<Anime> listAll() {
		return animeRepository.findAll();
	}
	
	public Anime findById(Long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}
	
	public List<Anime> findByName(String name) {
		return animeRepository.findByName(name);
	}
	
	public Anime save(AnimePostRequestBody animePostRequestBody) {
		return animeRepository.save(Anime.builder().name(animePostRequestBody.getName()).build());
	}
	
	public void replace(AnimePutRequestBody animePutRequestBody) {
		Anime savedAnime = findById(animePutRequestBody.getId());
		animeRepository.save(Anime.builder().id(savedAnime.getId()).name(animePutRequestBody.getName()).build());
	}
	
	public void delete(Long id) {
		animeRepository.delete(findById(id));
	}
}
