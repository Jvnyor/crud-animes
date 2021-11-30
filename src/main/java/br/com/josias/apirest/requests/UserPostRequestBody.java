package br.com.josias.apirest.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPostRequestBody {

	@Schema(description = "First name", example = "Fulano", required = true)
	@NotEmpty
	@NotNull
	private String firstName;
	
	@Schema(description = "Last name", example = "Silva", required = true)
	@NotEmpty
	@NotNull
	private String lastName;
	
	@Schema(description = "Full name", example = "Fulano Silva", required = true)
	@NotEmpty
	@NotNull
	private String fullName;
	
	@Schema(description = "Username", example = "fulano", required = true)
	@NotEmpty
	@NotNull
	private String username;
	
	@Schema(description = "E-mail", example = "fulano@mail.com", required = true)
	@NotEmpty
	@NotNull
	@Email
	private String email;
	
	@Schema(description = "Password", required = true)
	@NotEmpty
	@NotNull
	private String password;
	
}
