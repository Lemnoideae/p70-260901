package com.back.p67260811.domain.member.service;

import com.back.p67260811.domain.member.entity.Member;
import com.back.p67260811.standard.MyUtility;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthTokenService {

    private final String secretPattern =
            "abcdefghijklmnopqrstuvwxyz1234567890abcdefghijklmnopqrstuvwxyz1234567890";
    private final long expireMillis = 1000L * 60 * 10;

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
