package br.com.josias.apirest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.Customer;
import br.com.josias.apirest.repository.CustomerRepository;
import br.com.josias.apirest.requests.CustomerPostRequestBody;
import br.com.josias.apirest.requests.CustomerPutRequestBody;

@Service
public class CustomerService {
	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	private final CustomerRepository customerRepository;
	
	public List<Customer> listAll() {
		return customerRepository.findAll();
	}
	
	public Customer findById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer not found"));
	}
	
	public List<Customer> findByName(String name) {
		return customerRepository.findByName(name);
	}
	
	public Customer save(CustomerPostRequestBody customerPostRequestBody) {
		return customerRepository.save(Customer.builder().name(customerPostRequestBody.getName()).email(customerPostRequestBody.getEmail()).phone(customerPostRequestBody.getPhone()).build());
	}
	
	public void replace(CustomerPutRequestBody customerPutRequestBody) {
		Customer savedCustomer = findById(customerPutRequestBody.getId());
		customerRepository.save(Customer.builder().id(savedCustomer.getId()).name(customerPutRequestBody.getName()).email(customerPutRequestBody.getEmail()).phone(customerPutRequestBody.getPhone()).build());
	}
	
	public void delete(Long id) {
		customerRepository.delete(findById(id));
	}
}