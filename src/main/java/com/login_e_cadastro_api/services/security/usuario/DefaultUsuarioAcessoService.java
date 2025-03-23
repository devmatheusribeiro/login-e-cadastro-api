package com.login_e_cadastro_api.services.security.usuario;

import com.login_e_cadastro_api.dto.local.UsuarioDetails;
import com.login_e_cadastro_api.enums.TipoUsuarioEnum;
import com.login_e_cadastro_api.infra.exception.AcessoNegadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DefaultUsuarioAcessoService implements UsuarioSecurityService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultUsuarioAcessoService.class);

    @Override
    public void verificarAcessoAoUsuarioPorId(Long id, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AcessoNegadoException("Usuário não autenticado.");
        }

        UsuarioDetails usuarioAutenticado = (UsuarioDetails) authentication.getPrincipal();

        if (!usuarioAutenticado.getId().equals(id) && !possuiPermissaoAdmin(usuarioAutenticado)) {
            logger.warn("Acesso negado: Usuário ID {} tentou acessar o perfil ID {} sem permissão.",
                    usuarioAutenticado.getId(), id);

            throw new AcessoNegadoException("Acesso negado: você não tem permissão para acessar este recurso.");
        }
    }

    private boolean possuiPermissaoAdmin(UsuarioDetails usuario) {
        return usuario.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(TipoUsuarioEnum.ADMIN.toString()));
    }
}
