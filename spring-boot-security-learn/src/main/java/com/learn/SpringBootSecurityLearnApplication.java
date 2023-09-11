package com.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.learn.models.User;
import com.learn.repo.UserRepository;

@SpringBootApplication
public class SpringBootSecurityLearnApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityLearnApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		User user1 = new User();
		user1.setUsername("sanjit");
		user1.setEmail("sanjit@gmail.com");
		user1.setPassword(this.bCryptPasswordEncoder.encode("darklord"));
		user1.setRoles("ROLE_ADMIN,ROLE_NORMAL");
		this.userRepository.save(user1);
		
		User user2 = new User();
		user2.setUsername("ram");
		user2.setEmail("ram@gmail.com");
		user2.setPassword(this.bCryptPasswordEncoder.encode("ramakrishna"));
		user2.setRoles("ROLE_NORMAL");
		this.userRepository.save(user2);
		
		
		
	}

}
