package br.com.josias.apirest.util;

import br.com.josias.apirest.requests.AnimeDTO;

public class AnimeDTOCreator {

	public static AnimeDTO createAnimeDTO() {
		return AnimeDTO.builder()
					.name(AnimeCreator.createAnimeToBeSaved().getName())
					.build();
	}
	
}
