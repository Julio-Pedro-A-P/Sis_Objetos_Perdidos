package com.example.sisobjetosperdidos.config;

import com.example.sisobjetosperdidos.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String correo = jwtUtil.obtenerCorreo(token);
                String rol = jwtUtil.obtenerRol(token);  // <-- NUEVO

                if (correo != null && rol != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null) {

                    var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));

                    var authToken = new UsernamePasswordAuthenticationToken(
                            correo, null, authorities
                    );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (Exception e) {
                System.out.println("⚠️ Token inválido: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
