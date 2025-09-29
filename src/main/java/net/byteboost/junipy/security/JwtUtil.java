package net.byteboost.junipy.security;

import java.util.Date;
import org.springframework.stereotype.Component;
import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    Dotenv dotenv = Dotenv.load();
    private final String SECRET = dotenv.get("JWT_SECRET"); 
    private final long EXPIRATION = Long.parseLong(dotenv.get("JWT_EXPIRATION_MS"));

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token).getBody().getSubject();
    }
}

