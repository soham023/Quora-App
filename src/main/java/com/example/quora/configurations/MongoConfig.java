package com.example.quora.configurations;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

//    public MongoClient mongoClient(){
//        return MongoClients.create("mongodb://localhost:27017/quora_db");
//    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient){
        return new MongoTemplate(mongoClient, "quora_db");
    }
}
