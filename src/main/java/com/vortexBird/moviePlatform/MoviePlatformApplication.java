package com.vortexBird.moviePlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableAsync
@SpringBootApplication(scanBasePackages={
		"com.vortexBird.moviePlatform.domain", "com.vortexBird.moviePlatform.persistence","com.vortexBird.moviePlatform.web"})
public class MoviePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviePlatformApplication.class, args);
	}

}
