package br.com.josias.apirest.requests;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomerPostRequestBody {

	@NotEmpty(message = "The customer name cannot be empty")
	@Schema(description = "Name", example = "Fulano", required = true)
	private String name;
	
	@NotEmpty(message = "The customer email cannot be empty")
	@Schema(description = "E-mail", example = "fulano@mail.com", required = true)
	private String email;
	
	@NotEmpty(message = "The customer phone cannot be empty")
	@Schema(description = "Phone Number", example = "(81)99999999", required = true)
	private String phone;
	
}
