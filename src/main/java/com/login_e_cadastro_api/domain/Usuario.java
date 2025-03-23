package com.login_e_cadastro_api.domain;

import com.login_e_cadastro_api.enums.TipoUsuarioEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "usuarios")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull(message = "Nome é obrigatório")
    @Column(unique = true)
    String userName;
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    String senha;
    @NotNull(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String nome;
    @NotNull(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    String email;
    @NotNull(message = "Senha é obrigatória")
    @Size(min = 10, max = 15, message = "Telefone deve ter entre 10 e 15 caracteres")
    String telefone;
    @Enumerated(EnumType.STRING)
    TipoUsuarioEnum tipo;
    @Column(nullable = false)
    boolean ativo = true;
}
