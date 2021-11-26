package br.com.josias.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.josias.apirest.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findByFullName(String fullName);
	
	public User findByEmail(String email);
	
	User findByUsername(String username);
	
}
