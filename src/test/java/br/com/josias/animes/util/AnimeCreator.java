package br.com.josias.animes.util;

import java.time.LocalDateTime;

import br.com.josias.animes.model.Anime;

public class AnimeCreator {
	
	public static Anime createAnimeToBeSaved() {
		return Anime.builder()
					.name("Hajime no Ippo")
					.build();
	}
	
	public static Anime createValidAnime() {
		return Anime.builder()
					.id(1L)
					.name("Hajime no Ippo")
					.createdAt(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.build();
	}
	
	public static Anime createValidUpdateAnime() {
		return Anime.builder()
					.id(1L)
					.name("Hajime no Ippo")
					.createdAt(DateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()))
					.build();
	}
	
}
