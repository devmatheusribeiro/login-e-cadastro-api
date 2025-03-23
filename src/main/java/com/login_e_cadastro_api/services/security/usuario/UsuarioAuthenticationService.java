package com.login_e_cadastro_api.services.security.usuario;

import org.springframework.security.core.Authentication;

public interface UsuarioAuthenticationService {
    Authentication getAuthentication();
}
