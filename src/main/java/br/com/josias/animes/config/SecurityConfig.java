package br.com.josias.animes.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.josias.animes.service.UserService;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		//Em banco de dados
		auth.userDetailsService(userService)
					.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/swagger-ui/**.js").permitAll()
				.antMatchers("/swagger-ui/**.js.map").permitAll()
				.antMatchers("/swagger-ui/**.html").permitAll()
				.antMatchers("/swagger-ui/**.css").permitAll()
				.antMatchers("/swagger-ui/**.css.map").permitAll()
				.antMatchers("/swagger-ui/webpack/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/swagger-ui/index.html").permitAll()
				.antMatchers("/swagger-ui/index.html#/").permitAll()
				.antMatchers("/v3/api-docs/**").permitAll()
				.antMatchers("/api/animes/registration").permitAll()
				.antMatchers("/api/animes/admin/**").hasRole("ADMIN")
				.antMatchers("/api/animes/user/**").hasRole("USER")
				.antMatchers("/swagger-ui/index.html#/crud-users/createUser").permitAll()
				.antMatchers("/swagger-ui/index.html#/crud-animes/**").hasRole("USER")
				.antMatchers("/swagger-ui/index.html#/crud-users/listAllUsers").hasRole("ADMIN")
				.antMatchers("/swagger-ui/index.html#/crud-users/findUserById").hasRole("ADMIN")
				.antMatchers("/swagger-ui/index.html#/crud-users/removeUser").hasRole("ADMIN")
				.antMatchers("/swagger-ui/index.html#/crud-users/replace").hasRole("ADMIN")
				
				.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.and()
				.httpBasic();
	}
	
	
	
}
