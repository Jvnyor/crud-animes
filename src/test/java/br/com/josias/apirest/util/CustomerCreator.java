package br.com.josias.apirest.util;

import br.com.josias.apirest.model.Customer;

public class CustomerCreator {

	public static Customer createCustomerToBeSaved() {
		return Customer.builder()
					.name("Junior")
					.email("junior@gmail.com")
					.phone("(81)999999999")
					.build();
	}
	
	public static Customer createValidCustomer() {
		return Customer.builder()
					.id(1L)
					.name("Junior")
					.email("junior@gmail.com")
					.phone("(81)999999999")
					.build();
	}
	
	public static Customer createValidUpdateCustomer() {
		return Customer.builder()
					.id(1L)
					.name("Junior")
					.email("junior@gmail.com")
					.phone("(81)999999999")
					.build();
	}
	
}
