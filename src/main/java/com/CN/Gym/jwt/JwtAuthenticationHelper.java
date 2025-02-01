package com.CN.Gym.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JwtAuthenticationHelper {

    private String secretKey = "";

    private final static long VALIDITY_TIMING = 25*60;

    public String getUserNameFromToken(String token) {
        Claims claims = this.getClaimsFromToken(token);
        return claims.getSubject();
    }

    public Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseSignedClaims(token)
                .getBody();
    }

    public boolean isTokenExiperd(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expDate = claims.getExpiration();

        return expDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Objects> claims = new HashMap<>();
       return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+VALIDITY_TIMING*1000))
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()), SignatureAlgorithm.HS512)
                .compact();
    }
}
