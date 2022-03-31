package com.Jvnyor.animes.client;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import com.Jvnyor.animes.model.Anime;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("https://crud-animes.herokuapp.com/api/animes/{id}", Anime.class, 3);
        log.info(entity);

        Anime object = new RestTemplate().getForObject("https://crud-animes.herokuapp.com/api/animes/{id}", Anime.class, 3);

        log.info(object);

        Anime[] animes = new RestTemplate().getForObject("https://crud-animes.herokuapp.com/api/animes/all", Anime[].class);

        log.info(Arrays.toString(animes));
        //@formatter:off
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("https://crud-animes.herokuapp.com/api/animes/all",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        //@formatter:on
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", kingdom, Anime.class);
//        log.info("saved anime {}", kingdomSaved);

        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange("https://crud-animes.herokuapp.com/api/animes/",
                HttpMethod.POST,
                new HttpEntity<>(samuraiChamploo, createJsonHeader()),
                Anime.class);

        log.info("saved anime {}", samuraiChamplooSaved);

        Anime animeToBeUpdated = samuraiChamplooSaved.getBody();
        animeToBeUpdated.setName("Samurai Champloo 2");

        ResponseEntity<Void> samuraiChamplooUpdated = new RestTemplate().exchange("https://crud-animes.herokuapp.com/api/animes/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
                Void.class,
                3);

        log.info("updated anime {}",samuraiChamplooUpdated);

        ResponseEntity<Void> samuraiChamplooDeleted = new RestTemplate().exchange("https://crud-animes.herokuapp.com/api/animes/{id}",
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