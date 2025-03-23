package com.login_e_cadastro_api.controllers;

import com.login_e_cadastro_api.constants.MappingConstants;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioCreateRequest;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioUpdateRequest;
import com.login_e_cadastro_api.dto.out.UsuarioResponse;
import com.login_e_cadastro_api.services.UsuarioService;
import com.login_e_cadastro_api.services.security.usuario.UsuarioAuthenticationService;
import com.login_e_cadastro_api.services.security.usuario.UsuarioSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(MappingConstants.Usuario.USUARIOS)
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final UsuarioSecurityService usuarioSecurityService;
    private final UsuarioAuthenticationService usuarioAuthenticationService;

    @GetMapping(MappingConstants.LISTAR)
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        List<UsuarioResponse> resposta = usuarioService.listarTodos();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        Authentication authentication = usuarioAuthenticationService.getAuthentication();
        usuarioSecurityService.verificarAcessoAoUsuarioPorId(id, authentication);

        UsuarioResponse resposta = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping(MappingConstants.REGISTRAR)
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody UsuarioCreateRequest request) {
        UsuarioResponse resposta = usuarioService.cadastrar(request);

        return ResponseEntity.ok(resposta);
    }

    @PutMapping(MappingConstants.ATUALIZAR + "/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable Long id, @RequestBody UsuarioUpdateRequest request) {
        Authentication authentication = usuarioAuthenticationService.getAuthentication();
        usuarioSecurityService.verificarAcessoAoUsuarioPorId(id, authentication);

        UsuarioResponse resposta = usuarioService.atualizar(id, request);

        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping(MappingConstants.DELETAR + "/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Authentication authentication = usuarioAuthenticationService.getAuthentication();
        usuarioSecurityService.verificarAcessoAoUsuarioPorId(id, authentication);

        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
