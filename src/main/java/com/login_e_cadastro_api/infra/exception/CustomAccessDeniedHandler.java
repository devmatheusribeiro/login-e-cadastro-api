package com.login_e_cadastro_api.infra.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login_e_cadastro_api.dto.out.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse errorResponse = new ErrorResponse("Acesso negado: Você não tem permissão para acessar este recurso.");
        String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}