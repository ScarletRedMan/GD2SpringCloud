package ru.scarletredman.gateway.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class GJP2 {

    public String hash(String input) {
        return DigestUtils.sha1Hex(input + "mI29fmAnxgTs");
    }
}
