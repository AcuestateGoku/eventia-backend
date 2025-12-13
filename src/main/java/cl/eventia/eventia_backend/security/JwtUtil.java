package cl.eventia.eventia_backend.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    // Ahora leemos el secreto desde application.properties
    // Si no lo encuentra, usará el valor por defecto después de los dos puntos (:)
    @Value("${jwt.secret:DeLeNiLoGaHeToDaVa_Eventia_123456789}")
    private String SECRET;

    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 2; // 2 horas

    private Key getSigningKey() {
        // Convierte el texto secreto a bytes para firmar
        return new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    }

    public String generarToken(String email, String rol) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(email)
                .claim("rol", rol)
                .setIssuedAt(ahora)
                .setExpiration(expiracion)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailDesdeToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getRolDesdeToken(String token) {
        return (String) getClaims(token).get("rol");
    }

    public boolean validarToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}