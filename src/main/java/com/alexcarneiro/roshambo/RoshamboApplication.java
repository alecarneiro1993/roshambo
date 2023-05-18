package com.alexcarneiro.roshambo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "repositories")
@ComponentScan(basePackages = { "com.alexcarneiro.roshambo.*" })
@EntityScan(basePackages = { "entities" })   
public class RoshamboApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoshamboApplication.class, args);
	}

}
