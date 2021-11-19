package br.com.josias.apirest.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.josias.apirest.model.Customer;
import br.com.josias.apirest.util.CustomerCreator;

@DataJpaTest
@DisplayName("Tests for customer repository")
class CustomerRepositoryTest {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerRepositoryTest(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	@Test
	@DisplayName("Tests persists customer when successful")
	void save_PersistCustomer_WhenSuccessful() {
		
		Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();
		
		Customer customerSaved = this.customerRepository.save(customerToBeSaved);
		
		Assertions.assertThat(customerSaved).isNotNull();
		
		Assertions.assertThat(customerSaved.getId()).isNotNull();
		
		Assertions.assertThat(customerSaved.getName()).isEqualTo(customerToBeSaved.getName());
	}

	@Test
	@DisplayName("Tests updates customer when successful")
	void save_UpdatesCustomer_WhenSuccessful() {
		
		Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();
		
		Customer customerSaved = this.customerRepository.save(customerToBeSaved);
		
		customerSaved.setName("Junin");
		
		Customer customerUpdated = this.customerRepository.save(customerSaved);
		
		Assertions.assertThat(customerUpdated).isNotNull();
		
		Assertions.assertThat(customerUpdated.getId()).isNotNull();
		
		Assertions.assertThat(customerUpdated.getName()).isEqualTo(customerSaved.getName());
	}
	
	@Test
	@DisplayName("delete removes customer when successful")
	void delete_RemovesCustomer_WhenSuccessful() {
		
		Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();
		
		Customer customerSaved = this.customerRepository.save(customerToBeSaved);
		
		this.customerRepository.delete(customerSaved);
		
		Optional<Customer> customerOptional = this.customerRepository.findById(customerSaved.getId());
		
		Assertions.assertThat(customerOptional).isEmpty();
	}
	
	@Test
	@DisplayName("findByName returns list of customer when successful")
	void findByName_ReturnsListOfCustomer_WhenSuccessful() {
		
		Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();
		
		Customer customerSaved = this.customerRepository.save(customerToBeSaved);
		
		String name = customerSaved.getName();
		
		List<Customer> customers = this.customerRepository.findByName(name);
		
		Assertions.assertThat(customers)
					.isNotEmpty()
					.contains(customerSaved);
	}
	
	@Test
	@DisplayName("findByName returns a empty list of customers when customer is not found")
	void findByName_ReturnsEmptyListOfCustomers_WhenCustomerIsNotFound() {
		
		List<Customer> customers = this.customerRepository.findByName("test");
		
		Assertions.assertThat(customers).isEmpty();
	}
}
