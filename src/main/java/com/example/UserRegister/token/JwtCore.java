package com.example.UserRegister.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Date;

@Component
public class JwtCore {
    @Value("${testing.app.secretKey}")
    private String key;
@Value("${testing.app.timeLife}")
    private int lifeTime;

public String generateToken(Authentication authentication){
    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + lifeTime);

    String token = Jwts.builder()
            .setSubject(username)
            .setIssuedAt( new Date())
            .setExpiration(expireDate)
            .signWith(SignatureAlgorithm.HS512, key)
            .compact();
    System.out.println("New token :");
    System.out.println(token);
    return token;
}
public String getNameFromJwt(String token){
    Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    return claims.getSubject();
}
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was exprired or incorrect",ex.fillInStackTrace());
        }
    }
}
