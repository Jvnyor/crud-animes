package br.com.josias.apirest.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.josias.apirest.model.User;
import br.com.josias.apirest.repository.UserRepository;
import br.com.josias.apirest.requests.UserPostRequestBody;
import br.com.josias.apirest.requests.UserPutRequestBody;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return Optional.ofNullable(userRepository.findByUsername(username))
							.orElseThrow(() -> new UsernameNotFoundException("User is not found"));
	}
	
	public User findById(Long id) {
		return userRepository.findById(id)
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found"));
	}
	
	public User save(UserPostRequestBody userPostRequestBody) {
		return userRepository.save(User.builder().name(userPostRequestBody.getName()).username(userPostRequestBody.getUsername()).password(userPostRequestBody.getPassword()).authorities("ROLE_USER").build());
	}
	
	public void replace(UserPutRequestBody userPutRequestBody) {
		User userSaved = findById(userPutRequestBody.getId());
		userRepository.save(User.builder().id(userSaved.getId()).name(userPutRequestBody.getName()).username(userPutRequestBody.getUsername()).password(userPutRequestBody.getPassword()).authorities("ROLE_USER").build());
	}
	
}
