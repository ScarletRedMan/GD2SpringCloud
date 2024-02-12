package ru.scarletredman.gateway.repository.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.repository.AccountIdentifierGenerator;

@Repository
@RequiredArgsConstructor
public class AccountIdentifierGeneratorImpl implements AccountIdentifierGenerator {

    private final static String SEQUENCE_KEY = "accountIdentifierSequence";

    private final ReactiveMongoTemplate template;

    @PostConstruct
    void initCounter() {
        template.getCollection("counter")
                .flatMap(collection -> Mono.fromDirect(collection.find(new Document("_id", SEQUENCE_KEY))))
                .hasElement()
                .subscribe(has -> {
                    if (has) return;

                    template.getCollection("counter")
                            .subscribe(collection -> {
                                var document = new Document();
                                document.put("_id", SEQUENCE_KEY);
                                document.put("seq", 1);

                                Mono.fromDirect(collection.insertOne(document)).subscribe();
                            });
                });
    }

    @Override
    public Mono<Long> getFreeIdentifier() {
        return template.getCollection("counter")
                .flatMap(collection -> {
                    var filter = new Document("_id", SEQUENCE_KEY);
                    var update = new Document("$inc", new Document("seq", 1));

                    return Mono.fromDirect(collection.findOneAndUpdate(filter, update));
                }).map(document -> document.getInteger("seq").longValue());
    }
}
