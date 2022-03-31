package com.Jvnyor.animes.util;

import com.Jvnyor.animes.requests.AnimeDTO;

public class AnimeDTOCreator {

	public static AnimeDTO createAnimeDTO() {
		return AnimeDTO.builder()
					.name(AnimeCreator.createAnimeToBeSaved().getName())
					.build();
	}
	
}
