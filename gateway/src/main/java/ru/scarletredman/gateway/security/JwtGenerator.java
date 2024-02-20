package ru.scarletredman.gateway.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.scarletredman.gateway.model.Account;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class JwtGenerator {

    private final ObjectMapper json;

    @Value("${GD2SPRING_SECRET_KEY:YOUR SECRET KEY HERE}")
    private String secretKey;

    private String encodedHeader;

    @SneakyThrows
    @PostConstruct
    void init() {
        encodedHeader = Base64.encodeBase64String(json.writeValueAsBytes(new Header("SHA256", "JWT")));
    }

    public Mono<String> generate(Mono<Account> accountMono) {
        return accountMono.map(account -> new Payload(account.getId(), account.getUsername(), account.getAuthorities()))
                .map(payload -> {
                    var encodedPayload = encodePayload(payload);
                    var signature = encodeSignature(encodedPayload);

                    return encodedHeader + "." + encodedPayload + "." + signature;
                });
    }

    @SneakyThrows
    private String encodePayload(Payload payload) {
        return Base64.encodeBase64String(json.writeValueAsBytes(payload));
    }

    private String encodeSignature(String encodedPayload) {
        return DigestUtils.sha256Hex(encodedHeader + "." + encodedPayload + secretKey);
    }

    private record Header(String alg, String typ) {}

    private record Payload(long id, String username, Collection<Authority> authorities) {}
}
