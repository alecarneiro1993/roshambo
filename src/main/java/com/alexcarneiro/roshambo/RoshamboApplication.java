package com.alexcarneiro.roshambo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "services", "com.alexcarneiro.roshambo.*" })
@EnableJpaRepositories(basePackages = { "repositories" })
@EntityScan(basePackages = { "entities" })   
public class RoshamboApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoshamboApplication.class, args);
	}

}
