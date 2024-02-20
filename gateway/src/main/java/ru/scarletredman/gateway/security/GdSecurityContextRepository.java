package ru.scarletredman.gateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.exception.AuthException;
import ru.scarletredman.gateway.security.authentication.Gjp2Authentication;
import ru.scarletredman.gateway.security.authentication.GuestAuthentication;
import ru.scarletredman.gateway.service.AccountService;

@RequiredArgsConstructor
public class GdSecurityContextRepository implements ServerSecurityContextRepository {

    private final AccountService accountService;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        return exchange.getFormData().map(MultiValueMap::toSingleValueMap)
                .flatMap(data -> {
                    var accountId = Long.parseLong(data.getOrDefault("accountID", "-1"));
                    var gjp2 = data.getOrDefault("gjp2", "");

                    if (accountId < 1 || gjp2.isEmpty()) return Mono.empty();

                    return accountService.auth(accountId, gjp2).onErrorResume(AuthException.class, ex -> Mono.empty());
                }).singleOptional()
                .map(optional -> optional.isPresent() ? new Gjp2Authentication(optional.get()) : new GuestAuthentication())
                .map(SecurityContextImpl::new);
    }
}
