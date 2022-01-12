package br.com.josias.animes.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import br.com.josias.animes.model.Anime;
import br.com.josias.animes.repository.AnimeRepository;
import br.com.josias.animes.requests.AnimeDTO;
import br.com.josias.animes.util.AnimeCreator;
import br.com.josias.animes.util.AnimeDTOCreator;
import br.com.josias.animes.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

	@Autowired
	@Qualifier(value = "testRestTemplate")
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@TestConfiguration
	@Lazy
	static class Config {
		@Bean(name = "testRestTemplate")
		public TestRestTemplate testRestTemplateCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:"+port);
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@Test
	@DisplayName("listAllAnimesPageable returns list of anime inside page object succesful")
	void listAllAnimesPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccesful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectName = savedAnime.getName();
		
		PageableResponse<Anime> animePage = testRestTemplate.exchange("/api/animes", HttpMethod.GET, null,
				new ParameterizedTypeReference<PageableResponse<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectName);
	}
	
	@Test
    @DisplayName("listAllAnimesNonPageable returns list of animes when successful")
    void listAllAnimesNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        
        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplate.exchange("/api/animes/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

	@Test
    @DisplayName("findAnimeById returns anime when successful")
    void findAnimeById_ReturnsAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
        Long id = savedAnime.getId();

        Anime anime = testRestTemplate.getForObject("/api/animes/{id}", Anime.class, id);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(id);
    }
	
	@Test
    @DisplayName("findAnimeByName returns list of anime when successful")
    void findAnimeByName_ReturnsListOfAnime_WhenSuccessful(){
		animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		String expectName = AnimeCreator.createValidAnime().getName();
		String url = String.format("/api/animes/find?name=%s", expectName);
		List<Anime> animes = testRestTemplate.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
    }
	
	@Test
    @DisplayName("findAnimeByName returns an empty list of anime when anime is not found")
    void findAnimeByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
		
		List<Anime> animes = testRestTemplate.exchange("/api/animes/find?name=dbz", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isEmpty();
		
    }
	
	@Test
    @DisplayName("createAnime returns anime when successful")
    void createAnime_ReturnsAnime_WhenSuccessful(){
		AnimeDTO animeDTO = AnimeDTOCreator.createAnimeDTO();
		
        ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/api/animes", animeDTO, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }
	
	@Test
    @DisplayName("replaceAnime updates anime when successful")
    void replaceAnime_UpdatesAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		savedAnime.setName("Boku no Hero");
		
		Long id = savedAnime.getId();
		
        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/api/animes/{id}",
        		HttpMethod.PUT,
        		new HttpEntity<>(savedAnime),
        		Void.class,
        		id);
        
        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
	
	@Test
    @DisplayName("removeAnime removes anime when successful")
    void removeAnime_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

        Long id = savedAnime.getId();
        
        ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/api/animes/{id}",
                HttpMethod.DELETE, null, Void.class, id);

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
	
}
