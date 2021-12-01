package br.com.josias.apirest.integration;

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

import br.com.josias.apirest.config.PasswordEncoder;
import br.com.josias.apirest.model.Anime;
import br.com.josias.apirest.model.User;
import br.com.josias.apirest.repository.AnimeRepository;
import br.com.josias.apirest.repository.UserRepository;
import br.com.josias.apirest.requests.AnimePostRequestBody;
import br.com.josias.apirest.util.AnimeCreator;
import br.com.josias.apirest.util.AnimePostRequestBodyCreator;
import br.com.josias.apirest.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {

	private static PasswordEncoder passwordEncoder;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private static final User USER = User.builder()
			.firstName("Josias")
			.lastName("Junior")
			.email("josias@gmail.com")
			.password(passwordEncoder.bCryptPasswordEncoder().encode("password"))
			.authorities("ROLE_USER")
			.build();
	
	private static final User ADMIN = User.builder()
			.firstName("Josias")
			.lastName("Junior")
			.email("junior@gmail.com")
			.password(passwordEncoder.bCryptPasswordEncoder().encode("password"))
			.authorities("ROLE_ADMIN,ROLE_USER")
			.build();
	
	@TestConfiguration
	@Lazy
	static class Config {
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:"+port)
					.basicAuthentication("josias@gmail.com", "password");
			return new TestRestTemplate(restTemplateBuilder);
		}
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:"+port)
					.basicAuthentication("junior@gmail.com", "password");
			return new TestRestTemplate(restTemplateBuilder);
		}
	}
	
	@Test
	@DisplayName("listAllAnimesPageable returns list of anime inside page object succesful")
	void listAllAnimesPageable_ReturnsListOfAnimesInsidePageObject_WhenSuccesful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		String expectName = savedAnime.getName();
		
		userRepository.save(USER);
		
		PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange("/api/v1/animes/user/", HttpMethod.GET, null,
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
        userRepository.save(USER);
        
        String expectedName = savedAnime.getName();

        List<Anime> animes = testRestTemplateRoleUser.exchange("/api/v1/animes/user/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Anime>>() {
                }).getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
    }

	@Test
    @DisplayName("findById returns anime when successful")
    void findAnimeById_ReturnsAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		userRepository.save(USER);
		
        Long expectedId = savedAnime.getId();

        Anime anime = testRestTemplateRoleUser.getForObject("/api/v1/animes/user/{id}", Anime.class, expectedId);

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
    }
	
	@Test
    @DisplayName("findByName returns list of anime when successful")
    void findAnimeByName_ReturnsListOfAnime_WhenSuccessful(){
		animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		userRepository.save(USER);
		
		String expectName = AnimeCreator.createValidAnime().getName();
		String url = String.format("/api/v1/animes/user/find?name=%s", expectName);
		List<Anime> animes = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isNotEmpty()
				.hasSize(1);
		
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectName);
    }
	
	@Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
		
		userRepository.save(USER);
		
		List<Anime> animes = testRestTemplateRoleUser.exchange("/api/v1/animes/user/find?name=dbz", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>(){
		}).getBody();
		
		Assertions.assertThat(animes)
				.isNotNull()
				.isEmpty();
		
    }
	
	@Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful(){
		AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
		
		userRepository.save(USER);
		
        ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleUser.postForEntity("/api/v1/animes/user", animePostRequestBody, Anime.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
    }
	
	@Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		userRepository.save(USER);
		
		savedAnime.setName("Boku no Hero");
		
        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/api/v1/animes/user",
        		HttpMethod.PUT,
        		new HttpEntity<>(savedAnime),
        		Void.class);

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
	
	@Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
        userRepository.save(ADMIN);

        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/api/v1/animes/user/{id}",
                HttpMethod.DELETE, null, Void.class, savedAnime.getId());

        Assertions.assertThat(animeResponseEntity).isNotNull();

        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
	
	@Test
    @DisplayName("delete returns 403 anime when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin(){
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		userRepository.save(USER);
		
        ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/api/v1/animes/user/{id}",
        		HttpMethod.DELETE,
        		null,
        		Void.class,
        		savedAnime.getId()
        );

        Assertions.assertThat(animeResponseEntity).isNotNull();
        Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }
}
