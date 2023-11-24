package com.util;


import com.entity.User;
import com.model.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {

    @Value("${date-expiration}")
    private int dateExpiration;
    @Value("${jwt-secret}")
    private String secret;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    public JwtResponse generateToken2(String username) {
        Map<String, Object> claims = new HashMap<>();
        JwtResponse jwtToken = new JwtResponse();
        jwtToken.setAccessToken(createToken(claims, username));
        jwtToken.setRefreshToken(refreshToken(claims,username));
        return jwtToken;

    }

    private String createToken(Map<String, Object> claims, String subject) {
        Date oneHourLater = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
//        Date oneDayLater = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        log.info("Date expiration {}", oneHourLater);
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(oneHourLater)
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    private String refreshToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (long) dateExpiration * 24 *7))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
