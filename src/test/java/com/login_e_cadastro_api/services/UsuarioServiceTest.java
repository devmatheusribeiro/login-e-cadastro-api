package com.login_e_cadastro_api.services;

import com.login_e_cadastro_api.domain.Usuario;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioCreateRequest;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioUpdateRequest;
import com.login_e_cadastro_api.dto.out.UsuarioResponse;
import com.login_e_cadastro_api.enums.TipoUsuarioEnum;
import com.login_e_cadastro_api.repositories.UsuarioRepository;
import com.login_e_cadastro_api.services.security.senhas.SenhaSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private SenhaSecurityService senhaSecurityService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario();
        usuarioMock.setId(1L);
        usuarioMock.setUserName("userService");
        usuarioMock.setSenha("senha123");
        usuarioMock.setNome("Usuário Teste");
        usuarioMock.setEmail("usuario@teste.com");
        usuarioMock.setTelefone("11999999999");
        usuarioMock.setTipo(TipoUsuarioEnum.ADMIN);
    }

    @Test
    void testCadastrarUsuario() {
        UsuarioCreateRequest createRequest = new UsuarioCreateRequest();
        createRequest.setUserName("userService");
        createRequest.setSenha("senha123");
        createRequest.setNome("Usuário Teste");
        createRequest.setEmail("usuario@teste.com");
        createRequest.setTelefone("11999999999");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);
        UsuarioResponse response = usuarioService.cadastrar(createRequest);

        assertNotNull(response);
        assertEquals("userService", response.getUserName());
        assertEquals("Usuário Teste", response.getNome());
        assertEquals("usuario@teste.com", response.getEmail());
    }

    @Test
    void testListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioMock));
        List<UsuarioResponse> usuarios = usuarioService.listarTodos();
        assertEquals(1, usuarios.size());
    }

    @Test
    void atualizarUsuarioTest() {
        Long usuarioId = usuarioMock.getId();
        UsuarioUpdateRequest updateRequest = new UsuarioUpdateRequest();
        updateRequest.setEmail("emailnovo@teste.com");

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setId(usuarioId);
        usuarioAtualizado.setEmail("emailnovo@teste.com");

        when(usuarioRepository.findById(usuarioId)).thenReturn(Optional.of(usuarioAtualizado));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioAtualizado);

        UsuarioResponse updatedResponse = usuarioService.atualizar(usuarioId, updateRequest);

        assertEquals("emailnovo@teste.com", updatedResponse.getEmail());
    }

    @Test
    void excluirUsuarioTest() {
        Long usuarioId = usuarioMock.getId();
        when(usuarioRepository.existsById(usuarioId)).thenReturn(true);
        usuarioService.excluir(usuarioId);
        when(usuarioRepository.existsById(usuarioId)).thenReturn(false);
        assertFalse(usuarioRepository.existsById(usuarioId));
    }
}
