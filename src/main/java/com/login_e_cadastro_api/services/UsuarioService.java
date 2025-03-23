package com.login_e_cadastro_api.services;

import com.login_e_cadastro_api.domain.Usuario;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioCreateRequest;
import com.login_e_cadastro_api.dto.in.usuario.UsuarioUpdateRequest;
import com.login_e_cadastro_api.dto.out.UsuarioResponse;
import com.login_e_cadastro_api.enums.TipoUsuarioEnum;
import com.login_e_cadastro_api.infra.exception.RecursoNaoEncontradoException;
import com.login_e_cadastro_api.mappers.GenericMapper;
import com.login_e_cadastro_api.repositories.UsuarioRepository;
import com.login_e_cadastro_api.services.security.senhas.SenhaSecurityService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService implements CadastroService<UsuarioCreateRequest, UsuarioUpdateRequest, UsuarioResponse> {

    private final UsuarioRepository usuarioRepository;
    private final SenhaSecurityService senhaSecurityService;
    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Override
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> GenericMapper.map(usuario, UsuarioResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse buscarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        return GenericMapper.map(buscarUsuario(id), UsuarioResponse.class);
    }

    @Override
    @Transactional
    public UsuarioResponse cadastrar(@Valid UsuarioCreateRequest request) {
        logger.info("Iniciando cadastro de usuário com userName: {}", request.getUserName());

        Usuario usuario = criarUsuario(request);
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        logger.info("Usuário cadastrado com sucesso. ID: {}", usuarioSalvo.getId());
        return GenericMapper.map(usuarioSalvo, UsuarioResponse.class);
    }

    @Override
    @Transactional
    public UsuarioResponse atualizar(Long id, UsuarioUpdateRequest request) {
        Usuario usuarioExistente = buscarUsuario(id);

        if (request.getSenha() != null) {
            usuarioExistente.setSenha(senhaSecurityService.codificarSenha(request.getSenha()));
        }

        if (request.getNome() != null) {
            usuarioExistente.setNome(request.getNome());
        }

        if (request.getEmail() != null) {
            usuarioExistente.setEmail(request.getEmail());
        }

        if (request.getTelefone() != null) {
            usuarioExistente.setSenha(request.getTelefone());
        }

        Usuario usuarioSalvo = usuarioRepository.save(usuarioExistente);

        return GenericMapper.map(usuarioSalvo, UsuarioResponse.class);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id);
        }
        logger.info("Excluindo usuário com ID: {}", id);
        usuarioRepository.deleteById(id);
    }

    private Usuario criarUsuario(UsuarioCreateRequest request) {
        Usuario usuario = GenericMapper.map(request, Usuario.class);
        usuario.setSenha(senhaSecurityService.codificarSenha(request.getSenha()));
        usuario.setTipo(TipoUsuarioEnum.USUARIO); //por padrao será apenas USUARIO
        return usuario;
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }
}
