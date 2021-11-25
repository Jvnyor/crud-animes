package br.com.josias.apirest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.josias.apirest.model.User;
import br.com.josias.apirest.requests.UserPostRequestBody;
import br.com.josias.apirest.requests.UserPutRequestBody;
import br.com.josias.apirest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="API REST for register users")
@RequestMapping("/api")
public class UserController {

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	private final UserService userService;
	
	@GetMapping("/user")
	@Operation(summary = "List of all customers",description="Returns list of all Customers")
	public ResponseEntity<List<User>> list() {
		return ResponseEntity.ok(userService.listAll());
	}
	
	@GetMapping("/user/{id}")
	@Operation(summary = "Returns customer by id",description="Returns a Customer by Id")
	public ResponseEntity<User> findById(@PathVariable long id) throws Exception {
		return ResponseEntity.ok(userService.findById(id));
	}
	
	@GetMapping("/user/")
	@Operation(summary = "Returns customer by name",description="Returns a Customer by Name")
	public ResponseEntity<List<User>> findByName(@RequestParam String fullName) {
		return ResponseEntity.ok(userService.findByFullName(fullName));
	}
	
	@PostMapping
	@Operation(summary = "Save customers",description="Save Customer")
	public ResponseEntity<User> save(@RequestBody UserPostRequestBody userPostRequestBody) {
		
		return new ResponseEntity<>(userService.save(userPostRequestBody),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/admin/{id}")
	@Operation(summary = "Delete customer by id",description="Delete Customer by Id")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/admin")
	@Operation(summary = "replace customer by id",description="Replace Customer by Id")
	public ResponseEntity<Void> replace(@RequestBody UserPutRequestBody userPutRequestBody) {
		userService.replace(userPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}