package br.com.josias.animes.config;

import org.springframework.http.HttpMethod;
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
				.antMatchers("/register").permitAll()
				.antMatchers(HttpMethod.POST,"/api/animes/registration").permitAll()
				.antMatchers(HttpMethod.GET,"/api/animes/admin/").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/api/animes/admin/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST,"/api/animes/admin/").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT,"/api/animes/admin/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/api/animes/user").hasRole("USER")
				.antMatchers(HttpMethod.GET,"/api/animes/user/**").hasRole("USER")
				.antMatchers(HttpMethod.POST,"/api/animes/user").hasRole("USER")
				.antMatchers(HttpMethod.PUT,"/api/animes/user/**").hasRole("USER")
				.antMatchers(HttpMethod.DELETE,"/api/animes/user/**").hasRole("USER")
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
