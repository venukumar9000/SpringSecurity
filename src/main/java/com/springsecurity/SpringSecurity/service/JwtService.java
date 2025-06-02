package com.springsecurity.SpringSecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



@Component
public class JwtService {

    private static final String SECRET = "uE7OYrLxE9Xy+AQHHbD3GQv52E3h6JAcjTMC4G2E4tM=";

    @Autowired
    private UserInfoUserDetailService userInfoUserDetailService;
    // 🔐 Generate Token
    public String generateToken(String username){
        UserDetails userDetails = userInfoUserDetailService.loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());  // include roles in JWT
        return createToken(claims, username);
    }

    // 🛠 Token builder
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 mins
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔐 Get signing key
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 🔍 Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 🧠 Extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // ✅ Validate token
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // 🕒 Check expiration
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 📅 Get expiration date
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 🔓 Decode all claims
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
