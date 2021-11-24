package br.com.josias.apirest.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerPostRequestBody {

	@NotEmpty(message = "The customer name cannot be empty")
	@Schema(description = "Name", example = "Fulano", required = true)
	//Pode ser validado pelo front-end
	private String name;
	
	@NotEmpty(message = "The customer email cannot be empty")
	@Email(message="Insert a valid E-mail")
	@Schema(description = "E-mail", example = "fulano@mail.com", required = true)
	private String email;
	
	@NotEmpty(message = "The customer phone cannot be empty")
	@Schema(description = "Phone Number", example = "(81)99999-9999", required = true)
	//A máscara pode ser implementada pelo próprio front-end
	private String phone;
	
}
