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
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRepository.findByEmail(email))
							.orElseThrow(() -> new UsernameNotFoundException("E-mail not found"));
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}
	
	public User findByEmail(String email) throws ResponseStatusException {
		return userRepository.findByEmail(email);
	}
	
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
																"User not found"));
	}
	
	public void save(UserDTO userDTO) {
		
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		if (emailExist(userDTO.getEmail())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail already exists");
		}
		
		userRepository.save(User.builder()
				.firstName(userDTO.getFirstName())
				.lastName(userDTO.getLastName())
				.email(userDTO.getEmail())
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
				.email(userDTO.getEmail())
				.build());
		
	}
	
	public void delete(Long id) {
		userRepository.delete(findById(id));
	}
	
	private boolean emailExist(String email) {
		return userRepository.findByEmail(email) != null;
	}
	

}