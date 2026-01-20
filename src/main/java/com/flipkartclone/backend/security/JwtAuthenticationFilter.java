package com.flipkartclone.backend.security;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
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

        if (header != null) {
            String token = null;

            // Accept both 'Bearer <token>' or just the raw token
            if (header.startsWith("Bearer ")) {
                token = header.substring(7).trim();
            } else if (!header.isBlank()) {
                token = header.trim();
            }

            if (token != null && !token.isEmpty()) {
                try {
                    Claims claims = jwtUtil.parseClaims(token);

                    String email = claims.getSubject();
                    String role = claims.get("role", String.class); // ROLE_ADMIN
                    logger.info("JWT ROLE = " + role);

                    if (email != null &&
                            SecurityContextHolder.getContext().getAuthentication() == null) {

                        List<SimpleGrantedAuthority> authorities =
                                role != null
                                        ? List.of(new SimpleGrantedAuthority(role))
                                        : List.of();

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        email, null, authorities);

                        auth.setDetails(
                                new WebAuthenticationDetailsSource()
                                        .buildDetails(request));

                        SecurityContextHolder.getContext()
                                .setAuthentication(auth);
                    }

                } catch (Exception e) {
                    logger.warn("Invalid/Expired JWT : " + e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}