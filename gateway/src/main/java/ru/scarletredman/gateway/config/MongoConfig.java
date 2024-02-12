package ru.scarletredman.gateway.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
@Configuration
public class MongoConfig {

    @Value("${GD2SPRING_MONGO_CONNECTION_STRING:mongodb://localhost:27017/}")
    private String connectionString;

    @Value("${GD2SPRING_MONGO_DATABASE_NAME:test}")
    private String databaseName;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(connectionString);
    }

    @Bean
    ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), databaseName);
    }
}
