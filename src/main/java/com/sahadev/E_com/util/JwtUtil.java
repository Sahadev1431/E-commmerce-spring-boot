package com.sahadev.E_com.util;

import com.sahadev.E_com.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "32b289d565c4f4cb4774fd621c081786bca3e9cbd54d3792b39e89fa5195a2c02bf7b94329fa7ca7e9fea4cffae1c2ddbf30c9e3689699c42ebdd2dc6b187a14fc03ab620388c5c7c27d5292f925788a6a350dede4c59e8f829df9b9bcf7d1125a039ab0cfbfac6f66c91315998a57dd57fb1eb2ad09934182cbc689377d1d16e7411ad9e9c6b36153c18def9e31f915246089380dcbae592730262c742c9ef1dfe1553ed0977c73c1b2fd48a4d90b8193058e952e042cd8cf791958571db059a757cdc363f9a49a12dd70af97f7da11c6631958920f1f4655f2615d5be1ee9b95ce1c20428d3593aaf00abe375b6153ee916d50ac0b4109ab2dd12c6fbcfdd00ab915e93665276da28e8661c98bd430fb792270515c20009d7f8372aa2a2b97";

    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getSignKey() {
        byte [] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaims(String token, Function<Claims,T> resolver) {
        return resolver.apply(
                Jwts.parser()
                        .verifyWith(getSignKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
        );
    }

    public String extractUserName(String token) {
        return extractClaims(token,claims -> claims.getSubject());
    }

//    public String extractRole(String token) {
//        return extractClaims(token,claims -> claims.get("role",String.class));
//    }

    public boolean validateToken(String token,User user) {
        return extractUserName(token).equals(user.getEmail()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token,claims -> claims.getExpiration().before(new Date()));
    }
}
