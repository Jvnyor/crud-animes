package br.com.josias.animes.util;

import br.com.josias.animes.requests.AnimeDTO;

public class AnimeDTOCreator {

	public static AnimeDTO createAnimeDTO() {
		return AnimeDTO.builder()
					.name(AnimeCreator.createAnimeToBeSaved().getName())
					.build();
	}
	
}
