package com.login_e_cadastro_api.infra.security;

import com.login_e_cadastro_api.domain.Usuario;
import com.login_e_cadastro_api.dto.local.UsuarioDetails;
import com.login_e_cadastro_api.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o userName: " + userName));

        // Verifica se o usuário está ativo
        if (!usuario.isAtivo()) {
            throw new UsernameNotFoundException("Usuário inativo: " + userName);
        }

        return new UsuarioDetails(
                usuario.getId(),
                usuario.getUserName(),
                usuario.getSenha(), // Senha criptografada
                usuario.getTipo().toString()
        );
    }
}
