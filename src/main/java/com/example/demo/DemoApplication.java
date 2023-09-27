package com.example.demo;

import com.example.demo.enums.Role;
import com.example.demo.objects.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class DemoApplication {

	private final AuthUserRepository authUserRepository;

	public DemoApplication(AuthUserRepository authUserRepository) {
		this.authUserRepository = authUserRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public CommandLineRunner runner(){
		return args -> {
			try {
				AuthUser director = AuthUser.builder()
						.active(true)
						.password("11223344")
						.workDate(LocalDate.now())
						.firstName("manager")
						.lastName("director")
						.role(Role.DIRECTOR)
						.username("admin1234")
						.build();
				authUserRepository.save(director);
			}catch (Exception ignore){}
		};
	}
}
