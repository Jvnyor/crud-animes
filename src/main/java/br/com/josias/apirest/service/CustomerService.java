package br.com.josias.apirest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.Customer;
import br.com.josias.apirest.repository.CustomerRepository;
import br.com.josias.apirest.requests.CustomerPostRequestBody;
import br.com.josias.apirest.requests.CustomerPutRequestBody;

@Service
public class CustomerService implements UserDetailsService {
	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	private final CustomerRepository customerRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return Optional.ofNullable(customerRepository.findByUsername(username))
							.orElseThrow(() -> new UsernameNotFoundException("User is not found"));
	}
	
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
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return customerRepository.save(Customer.builder().name(customerPostRequestBody.getName()).username(customerPostRequestBody.getUsername()).password(passwordEncoder.encode(customerPostRequestBody.getPassword())).email(customerPostRequestBody.getEmail()).authorities("ROLE_USER").build());
	}
	
	public void replace(CustomerPutRequestBody customerPutRequestBody) {
		Customer savedCustomer = findById(customerPutRequestBody.getId());
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		customerRepository.save(Customer.builder().id(savedCustomer.getId()).name(customerPutRequestBody.getName()).username(customerPutRequestBody.getUsername()).password(passwordEncoder.encode(customerPutRequestBody.getPassword())).email(customerPutRequestBody.getEmail()).authorities("ROLE_USER").build());
	}
	
	public void delete(Long id) {
		customerRepository.delete(findById(id));
	}
}