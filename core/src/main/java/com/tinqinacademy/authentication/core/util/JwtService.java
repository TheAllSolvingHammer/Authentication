package com.tinqinacademy.authentication.core.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.token.secret.key}")
    private String secretKey;

    @Value("${security.token.expiration}")
    private Long jwtExpiration;
    public String generateToken(Map<String, String> claims)
    {
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("username").toString();
    }

    public Claims extractAllClaims(String token) {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    public boolean isTokenValid(String token) {
        return extractExpiration(token).after(new Date(System.currentTimeMillis()));
    }

    private Date extractExpiration(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);  // Extract the username from the token
        return username.equals(userDetails.getUsername()) && isTokenValid(token);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
}
