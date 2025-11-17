package com.project.pin.unit;

import com.project.pin.entity.Comprador;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CompradorTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("CT11 - Telefone válido (não vazio)")
    public void telefoneValido() {
        Comprador c = new Comprador();
        c.setNome("Lucas");
        c.setEmail("lucas@email.com");
        c.setCpf("123.456.789-00");
        c.setUsername("lucas");
        c.setPassword("1234567890");
        c.setTelefone("123456789");
        Set<ConstraintViolation<Comprador>> violations = validator.validate(c);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT12 - Telefone inválido (vazio)")
    public void telefoneVazio() {
        Comprador c = new Comprador();
        c.setNome("Lucas");
        c.setEmail("lucas@email.com");
        c.setCpf("123.456.789-01");
        c.setUsername("lucas");
        c.setPassword("1234567890");
        c.setTelefone("");
        Set<ConstraintViolation<Comprador>> violations = validator.validate(c);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("telefone"));
    }
}
