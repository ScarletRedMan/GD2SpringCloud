package ru.scarletredman.gateway.repository.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Repository;
import ru.scarletredman.gateway.repository.AccountRepositoryIndexes;

@Log4j2
@RequiredArgsConstructor
@Repository
public class AccountRepositoryIndexesImpl implements AccountRepositoryIndexes {

    private final ReactiveMongoTemplate template;

    @PostConstruct
    void createIndexes() {
        var indexOps = template.indexOps("accounts");

        indexOps.ensureIndex(
                        new Index().on("lowerUsername", Sort.Direction.ASC).unique()
                ).then(indexOps.ensureIndex(
                        new Index().on("email", Sort.Direction.ASC).unique()
                )).doOnNext($ -> {
                    log.info("INDEXES INITED!");
                })
                .subscribe();
    }
}
