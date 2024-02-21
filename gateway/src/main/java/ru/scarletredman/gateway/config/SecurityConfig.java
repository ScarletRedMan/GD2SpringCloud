package ru.scarletredman.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.filter.DebugWebFilter;
import ru.scarletredman.gateway.security.GdSecurityContextRepository;
import ru.scarletredman.gateway.service.AccountService;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http, AccountService accountService) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);
        http.logout(ServerHttpSecurity.LogoutSpec::disable);
        http.formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        http.httpBasic(ServerHttpSecurity.HttpBasicSpec::disable);

        http.exceptionHandling(handler -> handler.accessDeniedHandler((exchange, denied) -> {
            var response = exchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);

            var buffer = response.bufferFactory().wrap("-1".getBytes(StandardCharsets.UTF_8));

            return response.writeWith(Mono.just(buffer));
        }));

        http.securityContextRepository(new GdSecurityContextRepository(accountService));

        http.authorizeExchange(auth -> auth
                .pathMatchers("/rest/**").permitAll()
                .pathMatchers("/accounts/loginGJAccount.php").permitAll()
                .pathMatchers("/accounts/registerGJAccount.php").permitAll()
                .anyExchange().authenticated()
        );

        http.addFilterBefore(new DebugWebFilter(), SecurityWebFiltersOrder.AUTHORIZATION);

        return http.build();
    }
}
