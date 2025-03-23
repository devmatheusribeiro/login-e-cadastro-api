package com.login_e_cadastro_api.constants;

public class MappingConstants {
    public static final String BASE_API = "/api";
    public static final String LISTAR = "/listar";
    public static final String REGISTRAR = "/registrar";
    public static final String ATUALIZAR = "/atualizar";
    public static final String DELETAR = "/deletar";

    public static class Auth {
        public static final String AUTH = BASE_API + "/auth";
        public static final String LOGIN = "/login";
    }

    public static class Usuario {
        public static final String USUARIOS = BASE_API + "/usuarios";
    }
}
