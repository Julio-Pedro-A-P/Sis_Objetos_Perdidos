package com.example.sisobjetosperdidos.util;

import com.example.sisobjetosperdidos.entity.UsuarioEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    //Usa una clave con longitud suficiente (>= 32 bytes para HS256).
    //Cambia esto por una variable de entorno en producción.
    private final String SECRET = "ClaveSuperSecretaParaFirmarTokensJwtDeEjemplo123456";
    private final long EXPIRATION = 1000 * 60 * 60; // 1 hora

    private Key key() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    //Genera token incluyendo el rol como claim
    public String generarToken(UsuarioEntity usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreoInstitucional())
                .claim("rol", usuario.getRol() != null ? usuario.getRol().name() : "ESTUDIANTE")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Si prefieres generar sólo con correo (mantén compatibilidad)
    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol != null ? rol : "ESTUDIANTE")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Obtener correo (subject)
    public String obtenerCorreo(String token) {
        return parseClaims(token).getSubject();
    }

    // Obtener rol desde claims
    public String obtenerRol(String token) {
        Object rol = parseClaims(token).get("rol");
        return rol != null ? rol.toString() : null;
    }

    // Validar token (lanza excepciones si no es válido)
    public boolean validarToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
