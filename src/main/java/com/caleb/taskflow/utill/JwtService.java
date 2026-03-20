package com.caleb.taskflow.utill;


import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import io.jsonwebtoken.*;

import java.util.Date;

@Component
public class JwtService {


    private final String SECRET;
    private final SecretKey key;

    public JwtService(@Value("${app.jwt.secret}") String secret) {
        this.SECRET = secret;
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    // generate token for given email
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // extract email from token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // check if token is expired
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return  expiration.before(new Date());
    }

    // check if token is valid
    public boolean isTokenValid(String token, String email) {
        String extractedEmail = extractEmail(token);

        return  (extractedEmail.equals(email) && !isTokenExpired(token));
    }

}
