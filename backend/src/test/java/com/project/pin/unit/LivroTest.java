package com.project.pin.unit;

import com.project.pin.entity.Autor;
import com.project.pin.entity.Livro;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class LivroTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // --- Método auxiliar para criar um livro válido ---
    private Livro criarLivroValido() {
        Autor autor = new Autor();
        autor.setNome("Autor Teste");
        autor.setSobre("Descrição do autor");
        autor.setImg("http://imagem.com/autor.jpg");

        Livro livro = new Livro();
        livro.setIsbn("ISBN-1234");
        livro.setNomeLivro("Livro Teste");
        livro.setPrecoUnit(50.0);
        livro.setPages(100);
        livro.setAnoPublicado(2023);
        livro.setImg("http://imagem.com/livro.jpg");
        livro.setAutor(autor);

        return livro;
    }

    @Test
    @DisplayName("CT17 - Valida ISBN obrigatório")
    void isbnObrigatorio() {
        Livro livro = criarLivroValido();
        livro.setIsbn("");

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("isbn"));
    }

    @Test
    @DisplayName("CT18 - Valida nome do livro obrigatório")
    void nomeLivroObrigatorio() {
        Livro livro = criarLivroValido();
        livro.setNomeLivro("");

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("nomeLivro"));
    }

    @Test
    @DisplayName("CT19 - Valida preço unitário positivo")
    void precoUnitarioPositivo() {
        Livro livro = criarLivroValido();
        livro.setPrecoUnit(-10.0);

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("precoUnit"));
    }

    @Test
    @DisplayName("CT20 - Valida número mínimo de páginas")
    void pagesMinimo() {
        Livro livro = criarLivroValido();
        livro.setPages(0);

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("pages"));
    }

    @Test
    @DisplayName("CT21 - Valida ano publicado mínimo")
    void anoPublicadoMinimo() {
        Livro livro = criarLivroValido();
        livro.setAnoPublicado(999);

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("anoPublicado"));
    }

    @Test
    @DisplayName("CT22 - Valida ano publicado máximo")
    void anoPublicadoMaximo() {
        Livro livro = criarLivroValido();
        livro.setAnoPublicado(10000);

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("anoPublicado"));
    }

    @Test
    @DisplayName("CT23 - Valida livro totalmente válido")
    void livroValido() {
        Livro livro = criarLivroValido();

        Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
        assertThat(violations).isEmpty();
    }
}
