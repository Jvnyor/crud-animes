package com.Jvnyor.animes.service;

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
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Jvnyor.animes.exception.BadRequestException;
import com.Jvnyor.animes.model.Anime;
import com.Jvnyor.animes.repository.AnimeRepository;
import com.Jvnyor.animes.util.AnimeCreator;
import com.Jvnyor.animes.util.AnimeDTOCreator;

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
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any(PageRequest.class)))
        .thenReturn(animePage);
		
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
    @DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findByIdOrThrowBadRequestException(1L);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
	
	@Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound(){
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        .thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
		.isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1L));
    }
	
	@Test
    @DisplayName("findByName returns page list of anime when successful")
    void findByName_ReturnsPageListOfAnime_WhenSuccessful(){
		String expectName = AnimeCreator.createValidAnime().getName();
		Page<Anime> animes = animeService.findByName("anime", PageRequest.of(1, 1));
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.toList().get(0).getName()).isEqualTo(expectName);
    }
	
	@Test
    @DisplayName("findByName returns an empty page list of anime when anime is not found")
    void findByName_ReturnsEmptyPageListOfAnime_WhenAnimeIsNotFound(){
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(Page.empty());
		
		Page<Anime> animes = animeService.findByName("anime", Pageable.ofSize(1));
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isEmpty();
		
    }
	
	@Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        Anime anime = animeService.save(AnimeDTOCreator.createAnimeDTO());

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
	
	@Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){

//		Anime savedAnime = animeRepositoryMock.save(AnimeCreator.createValidAnime());
		
		Assertions.assertThatCode(() -> animeService.replace(1L,AnimeDTOCreator.createAnimeDTO()))
				.doesNotThrowAnyException();

    }
	
	@Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful(){

		Assertions.assertThatCode(() -> animeService.delete(1L))
				.doesNotThrowAnyException();
		
    }
}
