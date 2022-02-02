package br.com.josias.animes.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.josias.animes.exception.BadRequestException;
import br.com.josias.animes.model.Anime;
import br.com.josias.animes.repository.AnimeRepository;
import br.com.josias.animes.requests.AnimeDTO;
import br.com.josias.animes.util.DateUtil;

@Service
public class AnimeService {

	@Autowired
	private AnimeRepository animeRepository;

	public Page<Anime> listAllPageable(Pageable pageable) {
		return animeRepository.findAll(pageable);
	}
	
	public List<Anime> listAllNonPageable() {
		return animeRepository.findAll();
	}
	
	public Anime findByIdOrThrowBadRequestException(Long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Anime not found"));
	}
	
	public List<Anime> findByName(String name) {
		return animeRepository.findByName(name);
	}
	
	@Transactional
	public Anime save(AnimeDTO animeDTO) {
		return animeRepository.save(Anime.builder().name(animeDTO.getName()).createdAt(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())).build());
	}
	
	public Anime replace(Long id,AnimeDTO animeDTO) {
		Anime savedAnime = findByIdOrThrowBadRequestException(id);
		return animeRepository.save(Anime.builder().id(savedAnime.getId()).name(animeDTO.getName()).createdAt(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now())).build());
	}
	
	public void delete(Long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}
}
