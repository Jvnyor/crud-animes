package br.com.josias.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.josias.apirest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
}
