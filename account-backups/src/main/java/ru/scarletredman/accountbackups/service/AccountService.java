package ru.scarletredman.accountbackups.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.scarletredman.accountbackups.model.AccountData;

@FeignClient("gateway")
public interface AccountService {

    @GetMapping("/rest/auth")
    AccountData auth(@RequestHeader("GD2S-AccountId") int accountId, @RequestHeader("GD2S-GJP2") String gjp2);
}
