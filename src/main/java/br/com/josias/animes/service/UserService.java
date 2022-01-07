package br.com.josias.animes.service;

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

import br.com.josias.animes.model.User;
import br.com.josias.animes.model.roles.UserRole;
import br.com.josias.animes.repository.UserRepository;
import br.com.josias.animes.requests.UserDTO;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRepository.findByUsername(username))
							.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}
	
	public User findByUsername(String username) throws ResponseStatusException {
		return userRepository.findByUsername(username);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
																"User not found"));
	}
	
	public void save(UserDTO userDTO) {
		
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		if (usernameExist(userDTO.getUsername())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
		}
		
		userRepository.save(User.builder()
				.firstName(userDTO.getFirstName())
				.lastName(userDTO.getLastName())
				.username(userDTO.getUsername())
				.password(passwordEncoder.encode(userDTO.getPassword()))
				.authorities(String.valueOf(UserRole.USER))
				.build());
		
	}
	
	public void replace(Long id,UserDTO userDTO) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories
				.createDelegatingPasswordEncoder();
		
		User savedUser = userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not exists"));
		userRepository.save(User.builder()
				.id(savedUser.getId())
				.firstName(userDTO.getFirstName())
				.lastName(userDTO.getLastName())
				.password(passwordEncoder.encode(userDTO.getPassword()))
				.username(userDTO.getUsername())
				.build());
		
	}
	
	public void delete(Long id) {
		userRepository.delete(findById(id));
	}
	
	private boolean usernameExist(String username) {
		return userRepository.findByUsername(username) != null;
	}
	

}