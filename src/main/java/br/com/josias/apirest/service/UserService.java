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

import br.com.josias.apirest.model.User;
import br.com.josias.apirest.repository.UserRepository;
import br.com.josias.apirest.requests.UserPostRequestBody;
import br.com.josias.apirest.requests.UserPutRequestBody;

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
							.orElseThrow(() -> new UsernameNotFoundException("User is not found"));
	}
	
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
																"Customer not found"));
	}
	
	public List<User> findByFullName(String fullName) {
		return userRepository.findByFullName(fullName);
	}
	
	public User save(UserPostRequestBody userPostRequestBody) {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories
				.createDelegatingPasswordEncoder();
		
		if (emailExist(userPostRequestBody.getEmail()) || usernameExist(userPostRequestBody.getUsername())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail or Username already exists");
		}
		
		return userRepository.save(User.builder()
				.firstName(userPostRequestBody.getFirstName())
				.lastName(userPostRequestBody.getLastName())
				.fullName(userPostRequestBody.getFirstName()+" "+userPostRequestBody.getLastName())
				.username(userPostRequestBody.getUsername())
				.password(passwordEncoder.encode(userPostRequestBody.getPassword()))
				.email(userPostRequestBody.getEmail())
				.build());
		
	}
	
//	public void replace(UserPutRequestBody userPutRequestBody) {
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories
//				.createDelegatingPasswordEncoder();
//		
//		if (!idToReplaceUser(userPutRequestBody.getId())) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exists");
//		} else if (idToReplaceUser(userPutRequestBody.getId()) && emailExist(userPutRequestBody.getEmail()) || usernameExist(userPutRequestBody.getUsername()) && passwordExist(userPutRequestBody.getPassword())) {
//			User savedUser = findById(userPutRequestBody.getId());
//			userRepository.save(User.builder()
//					.id(savedUser.getId())
//					.firstName(userPutRequestBody.getFirstName())
//					.lastName(userPutRequestBody.getLastName())
//					.fullName(userPutRequestBody.getFirstName()+" "+userPutRequestBody.getLastName())
//					.username(userPutRequestBody.getUsername())
//					.password(passwordEncoder.encode(userPutRequestBody.getPassword()))
//					.email(userPutRequestBody.getEmail())
//					.build());
//		}
		
		
		
//	}
	
	public void delete(Long id) {
		userRepository.delete(findById(id));
	}
	
	private boolean usernameExist(String username) {
		return userRepository.findByUsername(username) != null;
	}
	
	private boolean idToReplaceUser(Long id) {
		return userRepository.findById(id) != null;
	}
	
	private boolean emailExist(String email) {
		return userRepository.findByEmail(email) != null;
	}
	

}