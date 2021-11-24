
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
public class UserPostRequestBody {

	@NotEmpty(message = "The name cannot be empty")
	@Schema(description = "Name", example = "Fulano", required = true)
	//Pode ser validado pelo front-end
	private String name;
	
	@NotEmpty(message = "The username cannot be empty")
	@Schema(description = "Username", example = "fulano", required = true)
	private String username;
	
	@NotEmpty(message = "The user email cannot be empty")
	@Email(message="Insert a valid E-mail")
	@Schema(description = "E-mail", example = "fulano@mail.com", required = true)
	private String email;
	
	@NotEmpty(message = "The user password cannot be empty")
	@Schema(description = "Password", required = true)
	private String password;
	
	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String setPassword() {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return passwordEncoder.encode(this.password);
	}
	
}
