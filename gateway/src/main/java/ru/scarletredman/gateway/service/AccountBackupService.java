package ru.scarletredman.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountBackupService {

    private final DiscoveryClient discovery;

    private final Random random = new Random();

    public Mono<String> getBackupServerURL() {
        return Flux.fromIterable(discovery.getInstances("account-backups"))
                .map(service -> service.getUri().toString())
                .collectList()
                .flatMap(list -> {
                    if (list.isEmpty()) return Mono.empty();

                    return Mono.just(list.get(random.nextInt(list.size())));
                });
    }
}
