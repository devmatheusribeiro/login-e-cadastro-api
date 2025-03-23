package com.login_e_cadastro_api.infra.security;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    @Value("${api.security.token.secret}")
    private String secretKey;

    @Bean
    public SecretKey secretKey() {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("A chave secreta não pode ser nula ou vazia.");
        }
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}