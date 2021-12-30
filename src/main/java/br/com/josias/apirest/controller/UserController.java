package br.com.josias.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.josias.apirest.model.User;
import br.com.josias.apirest.requests.UserDTO;
import br.com.josias.apirest.service.UserService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
//@Tag(name="Registration users and control users information")
@RequestMapping("/api/animes")
public class UserController {

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	private final UserService userService;
	
	@GetMapping("/admin")
	public ResponseEntity<List<User>> listAllUsers() {
		return ResponseEntity.ok(userService.listAll());
	}
	
	@GetMapping("/admin/{id}")
//	@Operation(summary = "Returns a user by id",description="Returns a user by Id")
	public ResponseEntity<User> findUserById(@PathVariable long id) throws Exception {
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@PostMapping("/registration")
//	@Operation(summary = "Create user",description="Create user")
	public ResponseEntity<String> createUser(@RequestBody UserDTO userPostRequestBody) {
		userService.save(userPostRequestBody);

		return new ResponseEntity<>("Usuário criado com sucesso!",HttpStatus.CREATED);
	}
	
	@DeleteMapping("/admin/{id}")
//	@Operation(summary = "Delete user by id",description="Delete user by Id")
	public ResponseEntity<Void> removeUser(@PathVariable Long id) throws Exception {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
//	@PutMapping("/user")
//	@Operation(summary = "replace customer by id",description="Replace Customer by Id")
//	public ResponseEntity<Void> replace(@RequestBody UserPutRequestBody userPutRequestBody) {
//		userService.replace(userPutRequestBody);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
	
}