package com.example.demo;

import com.example.demo.entites.UserApp;
import com.example.demo.repositories.UserAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private UserAppRepository userAppRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Hello World!");
	}

	@Override
	public void run(String... args) throws Exception {
		// Create and save a user
		Long a = 1L;
		UserApp user1 = new UserApp(a, "mio", "mio", "dd@exp.com", "9999");
		user1.setEmail("miiiiii@gmail.com");
		userAppRepository.save(user1);
		System.out.println("User saved: " + user1);
	}
}
