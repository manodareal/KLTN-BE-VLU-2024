package com.example.usermanagement.config;

import com.example.usermanagement.domain.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@Slf4j
public class JwtUtils {
    private final String secretKey;

    public JwtUtils() {
        this.secretKey = "KLTN";
        log.info("Secret Key: {}",secretKey);
    }

    public String createToken(User user) {
        // create token with these fields
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        claims.put("email",user.getEmail());
        claims.put("role",user.getRole().getRoleName());

        // build token with these claims and subject
        return buildToken(claims, user.getEmail());
    }

    private String buildToken(Map<String, Object> claims, String subject) {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();
        // Add 8 hours
        calendar.add(Calendar.HOUR_OF_DAY, 8);
        Date date = calendar.getTime();
        // Build token header-body-signature
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, User user) {
        final String email = extractEmail(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }
}
