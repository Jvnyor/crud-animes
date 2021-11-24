package br.com.josias.apirest.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.josias.apirest.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomerService customerService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		log.info("Password encoded admin {}", passwordEncoder.encode("admin"));
		
		//Em banco de dados
		auth.userDetailsService(customerService)
					.passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/admin/**").hasRole("ADMIN")
				.antMatchers("/api/user/**").hasRole("USER")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
				.and()
				.httpBasic()
				.and()
				.antMatcher("/api/")
				.authorizeRequests();
	}
	
	
	
}
