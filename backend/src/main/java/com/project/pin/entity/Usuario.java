package com.project.pin.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.*;



@Entity
@Table(name = "usuario")
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
@Data
@Getter
public abstract class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long id;
    @Column(name = "nome")
    protected String nome;

    @Email(message = "O campo (email) deve conter um email válido")
    @Column(name = "email")
    protected String email;

    @NotBlank(message = "O campo (cpf) não pode ser vazio")
    @Column(name = "cpf")
    protected String cpf;

    @Pattern(regexp = "\\S+", message = "O campo (username) não deve conter espaço")
    @Column(name = "username")
    protected String username;

    @Length(min = 10, max = 200, message = "A senha deve conter entre 10 e 200 caracteres")
    @Column(name = "password")
    protected String password;
}
