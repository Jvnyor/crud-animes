package br.com.josias.apirest.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.Anime;
import br.com.josias.apirest.repository.AnimeRepository;
import br.com.josias.apirest.util.AnimeCreator;
import br.com.josias.apirest.util.AnimePostRequestBodyCreator;
import br.com.josias.apirest.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

	@InjectMocks
	private AnimeService animeService;

	@Mock
	private AnimeRepository animeRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
			.thenReturn(animePage);
			
		BDDMockito.when(animeRepositoryMock.findAll())
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
        .thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
	}
	
	@Test
	@DisplayName("listAllPageable returns list of anime inside page object succesful")
	void listAllPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccesful() {
		String expectName = AnimeCreator.createValidAnime().getName();
		Page<Anime> animePage = animeService.listAllPageable(PageRequest.of(1, 1));
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectName);
	}
	
	@Test
	@DisplayName("listAllNonPageable returns list of anime inside page object succesful")
	void listAllNonPageable_ReturnsListOfAnimes_WhenSuccesful() {
		String expectName = AnimeCreator.createValidAnime().getName();
		List<Anime> animes = animeService.listAllNonPageable();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
	}
	
	@Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findById(1L);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
	
	@Test
    @DisplayName("findById throws ResponseStatusException when anime is not found")
    void findById_ThrowsResponseStatusException_WhenAnimeIsNotFound(){
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(ResponseStatusException.class)
		.isThrownBy(() -> animeService.findById(1L));
    }
	
	@Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful(){
		String expectName = AnimeCreator.createValidAnime().getName();
		List<Anime> animes = animeService.findByName("anime");
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
    }
	
	@Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        .thenReturn(Collections.emptyList());
		
		List<Anime> animes = animeService.findByName("anime");
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isEmpty();
		
    }
	
	@Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
	
	@Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){

		Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
				.doesNotThrowAnyException();

    }
	
	@Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){

		Assertions.assertThatCode(() -> animeService.delete(1L))
				.doesNotThrowAnyException();
		
    }
}