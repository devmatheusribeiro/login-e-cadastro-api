package com.login_e_cadastro_api.controllers;

import com.login_e_cadastro_api.dto.in.usuario.UsuarioCreateRequest;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioUpdateRequest;
import com.login_e_cadastro_api.dto.out.UsuarioResponse;
import com.login_e_cadastro_api.enums.TipoUsuarioEnum;
import com.login_e_cadastro_api.services.UsuarioService;
import com.login_e_cadastro_api.services.security.usuario.UsuarioAuthenticationService;
import com.login_e_cadastro_api.services.security.usuario.UsuarioSecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    @MockitoBean
    private UsuarioAuthenticationService usuarioAuthenticationService;

    @MockitoBean
    private UsuarioSecurityService usuarioSecurityService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioResponse usuarioAdminMock;
    private UsuarioResponse usuarioPadraoMock;

    @BeforeEach
    void setUp() {
        usuarioAdminMock = new UsuarioResponse(1L, "03212871857", "Usuário Admin", "usuario@admin.com", "11999999999", TipoUsuarioEnum.ADMIN.toString());
        usuarioPadraoMock = new UsuarioResponse(2L, "07386527098", "Usuário Padrao", "usuario@padrao.com", "11999999999", TipoUsuarioEnum.USUARIO.toString());
    }

    @Test
    @WithMockUser(username = "03212871857", roles = {"ADMIN"})
    void testListarUsuarios() throws Exception {
        Mockito.when(usuarioService.listarTodos()).thenReturn(List.of(usuarioAdminMock, usuarioPadraoMock));

       mockMvc.perform(get("/api/usuarios/listar"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[{'id':1,'userName':'03212871857','nome':'Usuário Admin','email':'usuario@admin.com','telefone':'11999999999','tipo':'ADMIN'}," +
                                                        "{'id':2,'userName':'07386527098','nome':'Usuário Padrao','email':'usuario@padrao.com','telefone':'11999999999','tipo':'USUARIO'}]"));
    }

    @Test
    @WithMockUser(username = "03212871857", roles = {"ADMIN"})
    void testarBuscarPorId() throws Exception {
        Mockito.when(usuarioAuthenticationService.getAuthentication()).thenReturn(mock(Authentication.class));
        Mockito.doNothing().when(usuarioSecurityService).verificarAcessoAoUsuarioPorId(2L, mock(Authentication.class));
        Mockito.when(usuarioService.buscarPorId(usuarioPadraoMock.getId())).thenReturn(usuarioPadraoMock);

        mockMvc.perform(get("/api/usuarios/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':2,'userName':'07386527098','nome':'Usuário Padrao','email':'usuario@padrao.com','telefone':'11999999999','tipo':'USUARIO'}"));
    }

    @Test
    void testarCadastrar() throws Exception {
        UsuarioCreateRequest createRequest = new UsuarioCreateRequest();
        createRequest.setUserName("07386527098");
        createRequest.setSenha("senha123");
        createRequest.setNome("Usuário Padrao");
        createRequest.setEmail("usuario@padrao.com");
        createRequest.setTelefone("11999999999");
        Mockito.when(usuarioService.cadastrar(Mockito.any(UsuarioCreateRequest.class))).thenReturn(usuarioPadraoMock);

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\":\"07386527098\",\"senha\":\"senha123\",\"nome\":\"Usuário Padrao\",\"email\":\"usuario@padrao.com\",\"telefone\":\"11999999999\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':2,'userName':'07386527098','nome':'Usuário Padrao','email':'usuario@padrao.com','telefone':'11999999999','tipo':'USUARIO'}"));
    }

    @Test
    @WithMockUser(username = "03212871857", roles = {"ADMIN"})
    void testarAtualizar() throws Exception {
        UsuarioUpdateRequest updateRequest = new UsuarioUpdateRequest();
        updateRequest.setEmail("usuario@admin2.com");

        Mockito.when(usuarioAuthenticationService.getAuthentication()).thenReturn(mock(Authentication.class));
        Mockito.doNothing().when(usuarioSecurityService).verificarAcessoAoUsuarioPorId(1L, mock(Authentication.class));
        Mockito.when(usuarioService.atualizar(Mockito.anyLong(), Mockito.any(UsuarioUpdateRequest.class))).thenReturn(usuarioAdminMock);

        usuarioAdminMock.setEmail("usuario@admin2.com");

        mockMvc.perform(put("/api/usuarios/atualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"usuario@admin2.com\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{'id':1,'userName':'03212871857','nome':'Usuário Admin','email':'usuario@admin2.com','telefone':'11999999999','tipo':'ADMIN'}"));
    }

    @Test
    @WithMockUser(username = "03212871857", roles = {"ADMIN"})
    void testarExcluir() throws Exception {
        Mockito.when(usuarioAuthenticationService.getAuthentication()).thenReturn(mock(Authentication.class));
        Mockito.doNothing().when(usuarioSecurityService).verificarAcessoAoUsuarioPorId(2L, mock(Authentication.class));
        Mockito.doNothing().when(usuarioService).excluir(Mockito.anyLong());

        mockMvc.perform(delete("/api/usuarios/deletar/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}