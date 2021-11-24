package br.com.josias.apirest.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.Customer;
import br.com.josias.apirest.repository.CustomerRepository;
import br.com.josias.apirest.util.CustomerCreator;
import br.com.josias.apirest.util.CustomerPostRequestBodyCreator;

//@ExtendWith(SpringExtension.class)
class CustomerServiceTest {

//	@InjectMocks
//	private CustomerService customerService;
//	
//	@Mock
//	private CustomerRepository customerRepositoryMock;
//	
//	@BeforeEach
//	void setUp() {
//		
//		BDDMockito.when(customerRepositoryMock.findAll()).thenReturn(List.of(CustomerCreator.createValidCustomer()));
//		
//		BDDMockito.when(customerRepositoryMock.findById(ArgumentMatchers.anyLong()))
//			.thenReturn(Optional.of(CustomerCreator.createValidCustomer()));
//		
//		BDDMockito.when(customerRepositoryMock.findByName(ArgumentMatchers.anyString()))
//		.thenReturn(List.of(CustomerCreator.createValidCustomer()));
//	}
//
//	@Test
//	@DisplayName("listAll returns list of customers when successful")
//	void listAll_ReturnsListOfCustomers_WhenSuccessful() {
//		String expectName = CustomerCreator.createValidCustomer().getName();
//		
//		List<Customer> customers = customerService.listAll();
//		
//		Assertions.assertThat(customers)
//					.isNotNull()
//					.isNotEmpty()
//					.hasSize(1);
//		
//		Assertions.assertThat(customers.get(0).getName()).isEqualTo(expectName);
//	}
//	
//	@Test
//    @DisplayName("findById returns customer when successful")
//	void findById_ReturnsCustomer_WhenSuccessful() {
//		Long expectedId = CustomerCreator.createValidCustomer().getId();
//		
//		Customer customer = customerService.findById(1L);
//		
//		Assertions.assertThat(customer).isNotNull();
//		
//		Assertions.assertThat(customer.getId()).isNotNull().isEqualTo(expectedId);
//	}
//	
//	@Test
//	@DisplayName("findById throws ResponseStatusException when customer is not found")
//	void findById_ThrowsResponseStatusException_WhenCustomerIsNotFound() {
//		BDDMockito.when(customerRepositoryMock.findById(ArgumentMatchers.anyLong()))
//			.thenReturn(Optional.empty());
//		
//		Assertions.assertThatExceptionOfType(ResponseStatusException.class)
//		.isThrownBy(() -> customerService.findById(1L));
//	}
//	
//	@Test
//	@DisplayName("findByName returns list of customer when successful")
//	void findByName_ReturnsListOfCustomer_WhenSuccessful() {
//		String expectName = CustomerCreator.createValidCustomer().getName();
//		
//		List<Customer> customers = customerService.findByName("test");
//		
//		Assertions.assertThat(customers)
//				.isNotNull()
//				.isNotEmpty()
//				.hasSize(1);
//		
//		Assertions.assertThat(customers.get(0).getName()).isEqualTo(expectName);
//	}
//	
//	@Test
//	@DisplayName("findByName returns empty list of customer when customer is not found")
//	void findByName_ReturnsEmptyListOfCustomer_WhenCustomerIsNotFound() {
//		BDDMockito.when(customerRepositoryMock.findByName(ArgumentMatchers.anyString()))
//		.thenReturn(Collections.emptyList());
//		
//		List<Customer> customers = customerService.findByName("test");
//		
//		Assertions.assertThat(customers)
//					.isNotNull()
//					.isEmpty();
//	}
//	
//	@Test
//	@DisplayName("save returns customer when successful")
//	void save_ReturnsCustomer_WhenSuccessful() {
//		Customer customer = customerService.save(CustomerPostRequestBodyCreator.createCustomerPostRequestBody());
//		
//		Assertions.assertThat(customer).isNotNull().isEqualTo(CustomerCreator.createValidCustomer());
//	}
}
