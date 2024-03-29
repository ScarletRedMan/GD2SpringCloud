package ru.scarletredman.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    Mono<String> hello() {
        return Mono.just("Hello world!");
    }
}
