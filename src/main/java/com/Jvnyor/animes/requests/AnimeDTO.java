package com.Jvnyor.animes.requests;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimeDTO {

	@Schema(description = "name", example = "Dragon Ball Z", required = true)
	@NotEmpty
	private String name;
}
