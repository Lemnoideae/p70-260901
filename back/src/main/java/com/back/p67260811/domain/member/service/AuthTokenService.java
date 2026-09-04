package com.back.p67260811.domain.member.service;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.standard.MyUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {

    @Value("${custom.jwt.secretPattern}")
    private String secretPattern;
    @Value("${custom.jwt.expireSeconds}")
    private long expireMillis;

    public String genAccessToken(Member member) {

        return MyUtility.jwt.toString(
                secretPattern,
                expireMillis,
                Map.of("id", member.getId(), "username", member.getUsername())
        );
    }

    public Map<String, Object> payloadOrNull(String jwt) {
        Map<String, Object> payload =
                MyUtility.jwt.payloadOrNull(jwt, secretPattern);

        if(payload == null) return null;

        int id = (int)payload.get("id");
        String username = (String)payload.get("username");


        return Map.of("id", id, "username", username);
    }
}
