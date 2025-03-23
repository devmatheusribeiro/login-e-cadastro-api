package com.login_e_cadastro_api.dto.out;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioResponse {
    Long id;
    String userName;
    String nome;
    String email;
    String telefone;
    String tipo;
}
