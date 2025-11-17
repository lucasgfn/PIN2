package com.project.pin.unit;

import com.project.pin.entity.Usuario;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UsuarioTest {

    private static Validator validator;

    public static class UsuarioTeste extends Usuario {
        public UsuarioTeste(String nome, String email, String cpf, String username, String password) {
            this.nome = nome;
            this.email = email;
            this.cpf = cpf;
            this.username = username;
            this.password = password;
        }
    }

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("CT01 - Senha com tamanho mínimo válido (10)")
    public void senhaTamanhoMinimoValido() {
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-00", "usuario1", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT02 - Senha com tamanho máximo válido (200)")
    public void senhaTamanhoMaximoValido() {
        String senhaMax = "a".repeat(200);
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-01", "usuario2", senhaMax);
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT03 - Senha abaixo do mínimo (inválido)")
    public void senhaAbaixoMinimo() {
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-02", "usuario3", "123456789");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    @DisplayName("CT04 - Senha acima do máximo (inválido)")
    public void senhaAcimaMaximo() {
        String senhaExcedida = "a".repeat(201);
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-03", "usuario4", senhaExcedida);
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("password"));
    }

    @Test
    @DisplayName("CT05 - Username válido (sem espaços)")
    public void usernameValido() {
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-10", "user", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT06 - Username inválido (contendo espaços)")
    public void usernameInvalido() {
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-11", "user teste", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("username"));
    }

    @Test
    @DisplayName("CT07 - Email inválido")
    public void emailInvalido() {
        Usuario u = new UsuarioTeste("Teste", "invalido-email", "123.456.789-21", "user2", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("email"));
    }

    @Test
    @DisplayName("CT08 - Email válido")
    public void emailValido() {
        Usuario u = new UsuarioTeste("Teste", "valido@email.com", "123.456.789-20", "user1", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT09 - CPF válido (não vazio)")
    public void cpfValido() {
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "123.456.789-30", "user1", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT10 - CPF inválido (vazio)")
    public void cpfInvalido() {
        Usuario u = new UsuarioTeste("Teste", "teste@email.com", "", "user2", "1234567890");
        Set<ConstraintViolation<Usuario>> violations = validator.validate(u);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("cpf"));
    }
}
