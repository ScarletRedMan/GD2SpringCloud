package ru.scarletredman.gateway.filter;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Log4j2(topic = "GeometryDash API (Input)")
public class DebugWebFilter implements WebFilter {

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        var request = exchange.getRequest();

        if (request.getPath().toString().startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        return exchange.getFormData().doOnNext(data -> {
            log.info("[%s] %s".formatted(request.getMethod().name(), request.getURI()));
            for (var entry: data.entrySet()) {
                log.info("    %s: %s".formatted(entry.getKey(), entry.getValue()));
            }
        }).then(chain.filter(exchange));
    }
}
