package br.com.josias.apirest.util;

import br.com.josias.apirest.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

	public static AnimePostRequestBody createAnimePostRequestBody() {
		return AnimePostRequestBody.builder()
					.name(AnimeCreator.createAnimeToBeSaved().getName())
					.build();
	}
	
}
