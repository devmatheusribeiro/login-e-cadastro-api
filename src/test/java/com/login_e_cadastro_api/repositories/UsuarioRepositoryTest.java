package com.login_e_cadastro_api.repositories;

import com.login_e_cadastro_api.domain.Usuario;
import com.login_e_cadastro_api.enums.TipoUsuarioEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testSalvarERecuperarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUserName("userRepo");
        usuario.setNome("Teste Repo");
        usuario.setEmail("teste@repo.com");
        usuario.setSenha("senha123");
        usuario.setTelefone("11999999999");
        usuario.setTipo(TipoUsuarioEnum.ADMIN);

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        Optional<Usuario> usuarioRecuperado = usuarioRepository.findById(usuarioSalvo.getId());

        assertTrue(usuarioRecuperado.isPresent());
        assertEquals("03212871857", usuarioRecuperado.get().getUserName());
        assertEquals("Teste Repo", usuarioRecuperado.get().getNome());
    }
}
