package com.poly.easylearning.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface IJwtService {

    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Claims extractAllClaims(String token);

    Key getSignInKey();

    String generateToken(Map<String, Object> extractClaims);

    String generateToken();

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> extracClaims, UserDetails userDetails);

    Boolean isValidToken(String token, UserDetails userDetails);

    Boolean isTokenExpired(String token);

    Date extractExpired(String token);
}
