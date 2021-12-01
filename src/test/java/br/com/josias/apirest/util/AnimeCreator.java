package br.com.josias.apirest.util;

import br.com.josias.apirest.model.Anime;

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
					.build();
	}
	
	public static Anime createValidUpdateAnime() {
		return Anime.builder()
					.id(1L)
					.name("Hajime no Ippo")
					.build();
	}
	
}
