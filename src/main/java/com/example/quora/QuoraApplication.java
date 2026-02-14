package com.example.quora;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class QuoraApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuoraApplication.class, args);
	}

	@Bean
	CommandLineRunner checkDb(MongoTemplate mongoTemplate){
		return args -> {
			System.out.println("Connected DB = " + mongoTemplate.getDb().getName());
		};
	}

}
