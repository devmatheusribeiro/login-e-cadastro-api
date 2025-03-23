package com.login_e_cadastro_api.dto.in.usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioUpdateRequest {
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String nome;
    @Email(message = "Email inválido")
    String email;
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    String senha;
    @Size(min = 10, max = 15, message = "Telefone deve ter entre 10 e 15 caracteres")
    String telefone;
}
