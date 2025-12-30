package com.krishnendu.SimpleWebApp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.JwkParserBuilder;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Service
public class JWTService {

    private String secretKey = "";
    private final String USER_ROLE = "USER";

    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String generateToken(String username) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", USER_ROLE);

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return (Claims) Jwts.parser().verifyWith(getKey()).build().parse(token).getPayload();
    }
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return getClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        // Check user is valid user && token is not expired
        String username = extractUsername(token);

        if( username.equals(userDetails.getUsername())  && !isTokenExpired(token) ) {
            return true;
        }
        return false;
    }
}
