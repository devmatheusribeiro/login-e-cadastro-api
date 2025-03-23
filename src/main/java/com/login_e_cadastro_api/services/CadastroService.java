package com.login_e_cadastro_api.services;

import java.util.List;

public interface CadastroService<C, U, R> {
    List<R> listarTodos();
    R buscarPorId(Long id);
    R cadastrar(C entidade);
    R atualizar(Long id, U entidade);
    void excluir(Long id);
}