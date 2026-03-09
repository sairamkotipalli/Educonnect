package com.edutech.progressive.jwt;

import com.edutech.progressive.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final UserRepository userRepository;

    // Keep secret & expiration consistent with your tests
    private final String secret = "secretKey000000_secretKey000000_secretKey000000";
    private final int expiration = 86400; // seconds (24 hours)

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        var user = userRepository.findByUsername(username);

        String role = (user != null && user.getRole() != null) ? user.getRole() : "";
        claims.put("roles", role);

        if (user != null) {
            claims.put("userId", user.getUserId());

            Integer studentId = (user.getStudent() != null) ? user.getStudent().getStudentId() : null;
            Integer teacherId = (user.getTeacher() != null) ? user.getTeacher().getTeacherId() : null;

            claims.put("studentId", studentId);
            claims.put("teacherId", teacherId);
        }

        Date now = new Date();
        Date exp = new Date(now.getTime() + expiration * 1000L);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = extractAllClaims(token).getExpiration();
        return expirationDate.before(new Date());
    }

    /**
     * Restored for compatibility with JwtRequestFilter: validate token against UserDetails.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}