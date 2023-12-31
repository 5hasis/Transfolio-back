package com.example.transfolio.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;


@Component
public class JwtUtils {

    public static String JWT_KEY;
    public static long EXPIRATION_TIME;

    @Value("${jwt.secret.key}")
    public void setSecretKey(String jwtKey) {
        JWT_KEY = jwtKey;
    }

    @Value("${jwt.expriration.time}")
    public void setExpirationTime(long jwtExpirationTime) {
        EXPIRATION_TIME = jwtExpirationTime;
    }

    public static String generateToken(String username) {

        SecretKey signKey = Jwts.SIG.HS256.key().build();

        String jwtToken = Jwts.builder()
                .subject("hi")
                .signWith(signKey)
                .compact();

        return jwtToken;

    }
}