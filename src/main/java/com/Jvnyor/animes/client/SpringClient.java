package com.Jvnyor.animes.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.Jvnyor.animes.model.Anime;
import com.Jvnyor.animes.wrapper.PageableResponse;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient {
	
	private static final String URL = "http://localhost:8080";
	
    public static void main(String[] args) {
        ResponseEntity<Anime> findByIdEntity = new RestTemplate().getForEntity(URL + "/api/animes/{id}", Anime.class, 5);
        log.info(findByIdEntity);
        
        Anime findByIdObject = new RestTemplate().getForObject(URL + "/api/animes/{id}", Anime.class, 5);

        log.info(findByIdObject);
        
        ResponseEntity<Anime> findByNameEntity = new RestTemplate().getForEntity(URL + "/api/animes/find?name=Dragon Ball Z", Anime.class);
        
        log.info(findByNameEntity);
        
        Anime findByNameObject = new RestTemplate().getForObject(URL + "/api/animes/find?name=Dragon Ball Z", Anime.class);

        log.info(findByNameObject);

        ResponseEntity<PageableResponse<Anime>> pageAnimes = new RestTemplate().exchange(URL + "/api/animes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageableResponse<Anime>>() {});
        
        log.info(pageAnimes);

//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("saved anime {}", kingdomSaved);

        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange(URL + "/api/animes/",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                Anime.class);

        log.info("saved anime {}", samuraiChamplooSaved);

        Anime animeToBeUpdated = samuraiChamplooSaved.getBody();
        
        animeToBeUpdated.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().exchange(URL + "/api/animes/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class,
                3);

        log.info("updated anime {}",samuraiChamplooUpdated);

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange(URL + "/api/animes/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                animeToBeUpdated.getId());

        log.info("deleted anime {}",samuraiChamplooDeleted);
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}