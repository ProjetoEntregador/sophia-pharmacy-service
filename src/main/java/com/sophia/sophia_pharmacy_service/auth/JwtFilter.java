package com.sophia.sophia_pharmacy_service.auth;

import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = extractToken(request);

            if (token != null) {
                String email = jwtUtil.extractEmail(token);

                if (isValidAuthentication(email)) {
                    authenticateUser(token, email);
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException ex) {
            handleUnauthorized(response);
        }
    }


    private String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }


    private boolean isValidAuthentication(String email) {
        return email != null && SecurityContextHolder.getContext().getAuthentication() == null;
    }


    private void authenticateUser(String token, String email) {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) return;

        boolean isValid = jwtUtil.validateToken(token, email);

        if (isValid) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), null, new ArrayList<>());

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }


    private void handleUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = String.format(
                "{ \"timestamp\": \"%s\", \"status\": 401, \"error\": \"Unauthorized\", \"message\": \"%s\" }",
                java.time.LocalDateTime.now(),
                "Faça login para acessar este recurso"
        );

        response.getWriter().write(json);
    }
}
