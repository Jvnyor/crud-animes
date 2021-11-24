package br.com.josias.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.josias.apirest.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public List<Customer> findByName(String name);
	
	Customer findByUsername(String username);
	
	
}
