package com.learn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;

import com.learn.services.CustomUserDetailService;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MySecurityConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrfz ->csrfz.disable())
		.authorizeHttpRequests((authz) -> authz
				.requestMatchers("/public/**").hasRole("NORMAL")
				.requestMatchers("/users/**").hasRole("ADMIN")
				.anyRequest()
				.authenticated())
				.userDetailsService(customUserDetailService)
				.formLogin(formlogin -> formlogin
						.loginPage("/signin")
						.permitAll()
						.loginProcessingUrl("/dologin")
						.defaultSuccessUrl("/users/"));
		
		return http.build();

	}

	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		UserDetails user = User.builder()
				.username("john")
				.password(this.passwordEncoder().encode("snow"))
				.roles("NORMAL")
				.build();

		UserDetails admin = User.builder()
				.username("sansa")
				.password(this.passwordEncoder().encode("satrk"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10);
	}

}
