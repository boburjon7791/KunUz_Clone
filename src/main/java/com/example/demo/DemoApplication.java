package com.example.demo;

import com.example.demo.config.MyApplicationListener;
import com.example.demo.enums.Role;
import com.example.demo.objects.AuthUser;
import com.example.demo.repositories.AuthUserRepository;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {

	private final AuthUserRepository authUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final MyApplicationListener myApplicationListener;
	private final ConcurrentMapCacheManager cacheManager;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Bean
	public CommandLineRunner runner(){
		return args -> {
			try {

				AuthUser director = AuthUser.builder()
						.active(true)
						.password(passwordEncoder.encode("11223344"))
						.workDate(LocalDate.now())
						.firstName("manager")
						.lastName("director")
						.role(Role.DIRECTOR)
						.online(false)
						.username("admin1234")
						.build();
				authUserRepository.save(director);
			}catch (Exception ignore){}
		};
	}
	@Bean
	public SecurityScheme createAPIKeySchema(){
		return new SecurityScheme().type(SecurityScheme.Type.HTTP)
				.bearerFormat("JWT")
				.scheme("bearer");
	}
	@Bean
	public OpenAPI openAPI(){
		return new OpenAPI().addSecurityItem(new SecurityRequirement()
				.addList("Bearer Authentication"))
				.components(new Components().addSecuritySchemes(
						"Bearer Authentication", createAPIKeySchema()))
				.info(new Info().title("Kun Uz Backend Rest API")
						.description("Description")
						.version("1.0").contact(new Contact().email("soliyevboburjon95@gmail.com")
								.name("Boburjon"))
						.license(new License().name("License of API")
								.url(myApplicationListener.hostName)));
	}
	@Bean
	public GroupedOpenApi director(){
		return GroupedOpenApi.builder()
				.pathsToMatch("/api.director/**",
						"/api.post/**",
						"/api.auth/**",
						"/api.multimedia/**")
				.group("Director")
				.build();
	}
	@Bean
	public GroupedOpenApi reporter(){
		return GroupedOpenApi.builder()
				.pathsToMatch("/api.reporter/**",
						"/api.post/**",
						"/api.auth/**",
						"/api.multimedia/**")
				.group("Reporter")
				.build();
	}
	@Bean
	public GroupedOpenApi editor(){
		return GroupedOpenApi.builder()
				.pathsToMatch("/api.editor/**",
						"/api.post/**",
						"/api.auth/**",
						"/api.multimedia/**")
				.group("Editor")
				.build();
	}
	@Bean
	public GroupedOpenApi simpleUser(){
		return GroupedOpenApi.builder()
				.pathsToMatch("/api.post/**",
						"/api.multimedia/get/**")
				.group("Simple User")
				.build();
	}
	@Scheduled(cron = "0 0 * * * *")
	public void deleteCaches(){
		cacheManager.getCacheNames()
				.forEach(s -> Objects.requireNonNull(cacheManager.getCache(s)).clear());
	}
}
