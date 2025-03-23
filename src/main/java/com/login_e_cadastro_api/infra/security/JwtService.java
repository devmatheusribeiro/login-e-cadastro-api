package com.login_e_cadastro_api.infra.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final SecretKey SECRET_KEY;
    private static final long EXPIRATION_TIME = 86400000; // 24 horas em milissegundos

    @Autowired
    public JwtService(SecretKey secretKey) {
        SECRET_KEY = secretKey;
    }

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .claim("userName", authentication.getName())
                .claim("roles", authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())) // Adiciona as roles
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY) // Valida o token com a chave secreta
                    .build()
                    .parseSignedClaims(token); // Faz o parsing do token
            return true; // Token válido
        } catch (Exception e) {
            return false; // Token inválido
        }
    }

    public String extractUserName(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userName", String.class); // Extrai a claim "userName"
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        // Extrai a claim "roles" do token
        Object rolesClaim = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles");

        if (rolesClaim instanceof List<?> rawList) {
            if (rawList.stream().allMatch(item -> item instanceof String)) {
                return (List<String>) rawList;
            }
        }

        return Collections.emptyList();
    }
}
