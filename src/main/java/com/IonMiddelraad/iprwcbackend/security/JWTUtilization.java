package com.IonMiddelraad.iprwcbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtilization {

    @Value("${jwt-secret}")
    private String secret;

    public String generateToken(String email, String roles) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("Employee Details")
                .withClaim("email", email)
                .withClaim("roles", roles)
                .withIssuedAt(new Date())
                .withIssuer("Enter your details: ")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("Employee Details")
                .withIssuer("Enter your details: ")
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("email").asString();
    }

}