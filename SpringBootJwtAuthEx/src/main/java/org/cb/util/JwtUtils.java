package org.cb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtils {

    AeadAlgorithm alg = Jwts.ENC.A256CBC_HS512;
    @Value("${app.token.secret}")
    private String secret;

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public Claims getClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        //        Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).provider()
        return Jwts.parser().decryptWith(key).build().parseEncryptedClaims(token).getPayload();
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        Map<String, Object> payload = new HashMap<>();
        payload.put("subject", subject);
        payload.put("issuer", "DigiTele Networks");
        payload.putAll(claims);
        return Jwts.builder().claims(payload).issuedAt(new Date(System.currentTimeMillis()))
                        .expiration(new Date(
                                        System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(2)))
                        //                        .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                        .encryptWith(key, Jwts.ENC.A128CBC_HS256).compact();
    }

    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        return generateToken(claims, subject);
    }

}
