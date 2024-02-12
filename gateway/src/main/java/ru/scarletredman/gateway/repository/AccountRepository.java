package ru.scarletredman.gateway.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.model.Account;

@Repository
public interface AccountRepository extends ReactiveCrudRepository<Account, Long>, AccountIdentifierGenerator {

    Mono<Account> findAccountByLowerUsername(String lowerUsername);
}
