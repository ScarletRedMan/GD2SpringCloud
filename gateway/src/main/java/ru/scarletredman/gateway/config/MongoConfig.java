package ru.scarletredman.gateway.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
public class MongoConfig {

    @Value("${GD2SPRING_MONGO_CONNECTION_STRING:mongodb://localhost:27017/}")
    private String connectionString;

    @Value("${GD2SPRING_MONGO_DATABASE_NAME:test}")
    private String databaseName;

    @Value("${GD2SPRING_MONGO_USERNAME:dev}")
    private String username;

    @Value("${GD2SPRING_MONGO_PASSWORD:dev}")
    private String password;

    @Bean
    MongoClient mongoClient() {
        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .credential(MongoCredential.createCredential(username, "admin", password.toCharArray()))
                .build());
    }

    @Bean
    ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), databaseName);
    }
}
