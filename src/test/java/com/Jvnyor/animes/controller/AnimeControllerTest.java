package com.Jvnyor.animes.controller;

import java.util.List;

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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.Jvnyor.animes.model.Anime;
import com.Jvnyor.animes.requests.AnimeDTO;
import com.Jvnyor.animes.service.AnimeService;
import com.Jvnyor.animes.util.AnimeCreator;
import com.Jvnyor.animes.util.AnimeDTOCreator;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

	@InjectMocks
	private AnimeController animeController;

	@Mock
	private AnimeService animeServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeServiceMock.listAllPageable(ArgumentMatchers.any()))
			.thenReturn(animePage);
		
		BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
        .thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(animePage);
		
		BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimeDTO.class)))
        .thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.when(animeServiceMock.replace(ArgumentMatchers.anyLong(),ArgumentMatchers.any(AnimeDTO.class)))
        .thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
	}
	
	@Test
	@DisplayName("listAllPageable returns list of anime inside page object succesful")
	void listAllPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccesful() {
		String expectName = AnimeCreator.createValidAnime().getName();
		Page<Anime> animePage = animeController.listAllPageable(null).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectName);
	}

	@Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnime_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(1L).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
	
	@Test
    @DisplayName("findByName returns list of anime when successful")
    void findByName_ReturnsPageListOfAnime_WhenSuccessful(){
		String expectName = AnimeCreator.createValidAnime().getName();
		Page<Anime> animes = animeController.findByName("anime", Pageable.ofSize(1)).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.toList().get(0).getName()).isEqualTo(expectName);
    }
	
	@Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyPageListOfAnime_WhenAnimeIsNotFound(){
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
        .thenReturn(Page.empty());
		
		Page<Anime> animes = animeController.findByName("anime", Pageable.ofSize(1)).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isEmpty();
		
    }
	
	@Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){

        Anime anime = animeController.save(AnimeDTOCreator.createAnimeDTO()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
    }
	
	@Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){
		
		Assertions.assertThatCode(() -> animeController.replace(1L,AnimeDTOCreator.createAnimeDTO()))
		.doesNotThrowAnyException();
		
		ResponseEntity<Anime> animeToReplace = animeController.replace(1L,AnimeDTOCreator.createAnimeDTO());
        
        Assertions.assertThat(animeToReplace).isNotNull();
        
        Assertions.assertThat(animeToReplace.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
	
	@Test
    @DisplayName("remove removes anime when successful")
    void remove_RemovesAnime_WhenSuccessful(){

		Assertions.assertThatCode(() -> animeController.remove(1L))
				.doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = animeController.remove(1L);
        
        Assertions.assertThat(entity).isNotNull();
        
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
