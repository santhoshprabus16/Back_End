package com.login.login.security;



import com.mongodb.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    private static final String SECRET_KEY= "HAGQ1VcGzbfs3YnzQk11AdCBuSJLpT2lQO3Wgg8Z9zBqtDuazlZ86rcCIlbB6V4o";


    public static String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public static boolean isValid(String token, UserDetails user){
        String username =extractUsername(token);
        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }
    private static boolean isTokenExpired(String token){

        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims =extractAllClaims(token);
        return resolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {

        return Jwts
                .parser()
                .setSigningKey(getSigingKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSigingKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(String.valueOf(SECRET_KEY));
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateToken(String user){
        return Jwts
                .builder()
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+24*60*60))
                .signWith(getSigingKey())
                .compact();
    }





}
