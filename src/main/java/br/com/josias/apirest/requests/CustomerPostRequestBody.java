package br.com.josias.apirest.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerPostRequestBody {

	@Schema(description = "Name", example = "Fulano", required = true)
	//Pode ser validado pelo front-end
	private String name;
	
	@Schema(description = "Username", example = "fulano", required = true)
	private String username;
	
	@Schema(description = "E-mail", example = "fulano@mail.com", required = true)
	private String email;
	
	@Schema(description = "Password", required = true)
	private String password;
	
}
