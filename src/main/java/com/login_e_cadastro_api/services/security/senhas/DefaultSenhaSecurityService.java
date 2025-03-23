package com.login_e_cadastro_api.services.security.senhas;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultSenhaSecurityService implements SenhaSecurityService {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String codificarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }
}