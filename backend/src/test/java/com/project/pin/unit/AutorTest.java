package com.project.pin.unit;

import com.project.pin.entity.Autor;
import com.project.pin.entity.Livro;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AutorTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("CT13 - Autor válido com todos os campos preenchidos")
    public void autorValido() {
        Autor autor = new Autor();
        autor.setNome("Raphael Montes");
        autor.setSobre("Romancista policial brasileiro");
        autor.setImg("https://img.com/foto.jpg");

        Set<ConstraintViolation<Autor>> violations = validator.validate(autor);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT14 - Autor com nome nulo ou vazio deve falhar")
    public void nomeObrigatorio() {
        Autor autor = new Autor();
        autor.setNome(null);

        Set<ConstraintViolation<Autor>> violations = validator.validate(autor);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nome"));

        autor.setNome("");
        violations = validator.validate(autor);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nome"));
    }

    @Test
    @DisplayName("CT15 - Campos opcionais podem ser nulos")
    public void camposOpcionaisNulos() {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        autor.setSobre(null);
        autor.setImg(null);

        Set<ConstraintViolation<Autor>> violations = validator.validate(autor);
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("CT16 - Adicionar e remover livros da lista")
    public void listaLivrosManipulavel() {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");

        Livro livro1 = new Livro();
        livro1.setNomeLivro("Livro 1");

        Livro livro2 = new Livro();
        livro2.setNomeLivro("Livro 2");

        // Adicionar
        autor.getListLivros().add(livro1);
        autor.getListLivros().add(livro2);

        List<Livro> livros = autor.getListLivros();
        assertThat(livros).hasSize(2).containsExactly(livro1, livro2);

        // Remover
        autor.getListLivros().remove(livro1);
        assertThat(autor.getListLivros()).hasSize(1).containsExactly(livro2);
    }
}
