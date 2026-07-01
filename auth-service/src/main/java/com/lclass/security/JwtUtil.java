package com.lclass.security;

import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secretKeyString;
	
	@Value("${jwt.expiration}")
	private long expirationTime;
	
	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.secretKeyString);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(String username, List<String> roles) {
		return Jwts.builder()
                .subject(username)
                // Adds custom claim named "roles" that our Gateway reads
                .claim("roles", roles) 
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
	}

}
