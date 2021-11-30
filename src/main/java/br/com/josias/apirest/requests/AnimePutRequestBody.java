package br.com.josias.apirest.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {

	@NotEmpty
	@NotNull
	private Long id;
	@NotEmpty
	@NotNull
	private String name;
}