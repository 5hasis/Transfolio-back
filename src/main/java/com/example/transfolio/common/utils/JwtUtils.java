package com.example.transfolio.common.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtils {

    private static String SECRET_KEY;
    private static long EXPIRATION_TIME;

    @Value("${jwt.secret.key}")
            public void setSecretKey(String jwtSecretKey) {
        SECRET_KEY = jwtSecretKey;
    }

    @Value("${jwt.expriration.time}")
    public void setExpirationTime(long jwtExpirationTime) {
        EXPIRATION_TIME = jwtExpirationTime;
    }

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}