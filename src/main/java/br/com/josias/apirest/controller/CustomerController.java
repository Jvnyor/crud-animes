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

import br.com.josias.apirest.model.Customer;
import br.com.josias.apirest.requests.CustomerPostRequestBody;
import br.com.josias.apirest.requests.CustomerPutRequestBody;
import br.com.josias.apirest.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name="API REST Customers")
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	private final CustomerService customerService;
	
	@GetMapping
	@Operation(summary = "List of all customers",description="Returns list of all Customers")
	public ResponseEntity<List<Customer>> list() {
		return ResponseEntity.ok(customerService.listAll());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Returns customer by id",description="Returns a Customer by Id")
	public ResponseEntity<Customer> findById(@PathVariable long id) throws Exception {
		return ResponseEntity.ok(customerService.findById(id));
	}
	
	@GetMapping("/")
	@Operation(summary = "Returns customer by name",description="Returns a Customer by Name")
	public ResponseEntity<List<Customer>> findByName(@RequestParam String name) {
		return ResponseEntity.ok(customerService.findByName(name));
	}
	
	@PostMapping("/admin")
	@Operation(summary = "Save customers",description="Save Customer")
	public ResponseEntity<Customer> save(@RequestBody CustomerPostRequestBody customerPostRequestBody) {
		
		return new ResponseEntity<>(customerService.save(customerPostRequestBody),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/admin/{id}")
	@Operation(summary = "Delete customer by id",description="Delete Customer by Id")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
		customerService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping("/admin")
	@Operation(summary = "replace customer by id",description="Replace Customer by Id")
	public ResponseEntity<Void> replace(@RequestBody CustomerPutRequestBody customerPutRequestBody) {
		customerService.replace(customerPutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}