package br.com.josias.apirest.util;

import br.com.josias.apirest.requests.AnimeDTO;

public class AnimePostRequestBodyCreator {

	public static AnimeDTO createAnimePostRequestBody() {
		return AnimeDTO.builder()
					.name(AnimeCreator.createAnimeToBeSaved().getName())
					.build();
	}
	
}
