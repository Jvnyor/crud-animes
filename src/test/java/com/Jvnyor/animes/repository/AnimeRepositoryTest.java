package com.Jvnyor.animes.repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.Jvnyor.animes.model.Anime;
import com.Jvnyor.animes.util.AnimeCreator;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

	@Autowired
	private AnimeRepository animeRepository;
	
	@Test
	@DisplayName("Save persist anime when sucessful")
	public void save_PersistAnime_WhenSucessful() {
		
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		Assertions.assertThat(animeSaved).isNotNull();
		
		Assertions.assertThat(animeSaved.getId()).isNotNull();
		
		Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
	}
 
	@Test
	@DisplayName("Save updates anime when Succesful")
	void save_UpdatesAnime_WhenSuccessful() {
		
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		animeSaved.setName("Overlord");
		
		Anime animeUpdated = this.animeRepository.save(animeSaved);
		
		Assertions.assertThat(animeUpdated).isNotNull();
		
		Assertions.assertThat(animeUpdated.getId()).isNotNull();
		
		Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

	}

	@Test
	@DisplayName("Delete updates anime when Succesful")
	void delete_RemovesAnime_WhenSuccessful() {
		
		Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		this.animeRepository.delete(animeSaved);
		
		Optional<Anime> animeOptional =  this.animeRepository.findById(animeSaved.getId());
		
		Assertions.assertThat(animeOptional).isEmpty();
	}
		
	@Test
	@DisplayName("Find by name returns page list of anime when Succesful")
	void findByName_ReturnsPageListOfAnime_WhenSuccessful() {
			
			Anime animeToBeSaved = AnimeCreator.createAnimeToBeSaved();
			
			Anime animeSaved = this.animeRepository.save(animeToBeSaved);
			
			String name = animeSaved.getName();
			
			Page<Anime> animes = this.animeRepository.findByName(name, Pageable.ofSize(1));
			
			Assertions.assertThat(animes)
					.isNotEmpty()
					.contains(animeSaved);
	}
	
	@Test
	@DisplayName("Find by name returns empty page list of anime when no anime is found")
	void findByName_ReturnsEmptyPageList_WhenAnimeIsNotFound() {
			
			Page<Anime> animes = this.animeRepository.findByName("inexistente", Pageable.ofSize(1));
			
			Assertions.assertThat(animes).isEmpty();
	}
	
}
