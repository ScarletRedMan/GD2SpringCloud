package ru.scarletredman.gateway.repository;

import reactor.core.publisher.Mono;

public interface AccountIdentifierGenerator {

    Mono<Long> getFreeIdentifier();
}
