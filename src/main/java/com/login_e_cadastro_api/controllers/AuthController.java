package com.login_e_cadastro_api.controllers;

import com.login_e_cadastro_api.constants.MappingConstants;
import com.login_e_cadastro_api.dto.in.LoginRequest;
import com.login_e_cadastro_api.dto.out.LoginResponse;
import com.login_e_cadastro_api.infra.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MappingConstants.Auth.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping(MappingConstants.Auth.LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword())
            );

            String token = jwtService.generateToken(authentication);

            return ResponseEntity.ok(new LoginResponse("Login realizado com sucesso!", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("UserName ou senha inválidos.", null));
        }
    }
}
