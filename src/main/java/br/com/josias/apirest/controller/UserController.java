package br.com.josias.apirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josias.apirest.model.User;
import br.com.josias.apirest.requests.UserPostRequestBody;
import br.com.josias.apirest.requests.UserPutRequestBody;
import br.com.josias.apirest.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="Users")
@RequestMapping("/user")
public class UserController {

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	private final UserService userService;
	@PostMapping
	public ResponseEntity<User> registerUser(UserPostRequestBody userPostRequestBody) {
		return new ResponseEntity<>(userService.save(userPostRequestBody), HttpStatus.CREATED);
	}
	@PutMapping
	public ResponseEntity<Void> alterUser(UserPutRequestBody userPutRequestBody) {
		userService.replace(userPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
