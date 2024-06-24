package com.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

    // Token validity duration (5 hours in milliseconds)
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000; 

    // Secret key for signing the JWT
    private String secret = "jwtTokenKey";

    // Retrieve username from JWT token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Retrieve a specific claim from the token using a claims resolver function
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims from the JWT token using the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate a JWT token for a user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Create the JWT token with the specified claims and subject (username)
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder() //Starts building a new JWT using the Jwts library, which provides a fluent API for constructing the token
                .setClaims(claims) // Sets the custom claims to be included in the JWT. Claims are pieces of information asserted about a subject (e.g., username, roles, etc.).
                .setSubject(subject) // Sets the subject of the JWT, which is typically the username of the user for whom the token is being generated.
                .setIssuedAt(new Date(System.currentTimeMillis())) // Sets the issue date of the token to the current date and time.
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) // Sets the expiration date of the token. The expiration date is calculated by adding the token validity duration (JWT_TOKEN_VALIDITY) to the current time. This means the token will expire after the specified duration.
                .signWith(SignatureAlgorithm.HS512, secret) //Signs the JWT using the HS512 algorithm and the secret key. This ensures the integrity of the token, meaning it can be verified by the recipient to ensure it has not been tampered with.
                .compact(); //  Builds the JWT and serializes it to a compact, URL-safe string. This is the final step in the token creation process.
    }

    // Validate the JWT token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
