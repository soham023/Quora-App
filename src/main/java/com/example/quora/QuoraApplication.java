package com.example.quora;

import com.example.quora.models.Question;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@SpringBootApplication
public class QuoraApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuoraApplication.class, args);
	}



	@Bean
	CommandLineRunner checkDb(ReactiveMongoTemplate template){
		return args -> {
			template.getMongoDatabase()
					.map(db -> db.getName())
					.doOnNext(name -> System.out.println(" REAL DB = " + name))
					.subscribe();
		};
	}


//	@Bean
//	CommandLineRunner test(ReactiveMongoTemplate template){
//		return args -> {
//			template.save(new Question())
//					.doOnNext(q -> System.out.println("Saved into DB: " + template.getCollectionName(Question.class)))
//					.subscribe();
//		};
//	}

}
