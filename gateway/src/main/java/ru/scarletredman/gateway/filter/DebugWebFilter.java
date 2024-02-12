package ru.scarletredman.gateway.filter;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Profile("dev")
@Log4j2(topic = "GeometryDash API (Input)")
@Component
public class DebugWebFilter implements WebFilter {

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var request = exchange.getRequest();

        if (request.getPath().toString().startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        log.info("[%s] %s".formatted(request.getMethod().name(), request.getURI()));

        return exchange.getFormData().doOnNext(data -> {
            for (var entry: data.entrySet()) {
                log.info("    %s: %s".formatted(entry.getKey(), entry.getValue()));
            }
        }).then(chain.filter(exchange));
    }
}
