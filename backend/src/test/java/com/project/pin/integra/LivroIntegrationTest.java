package com.project.pin.integra;

import com.project.pin.entity.Autor;
import com.project.pin.entity.Livro;
import com.project.pin.repository.AutorRepository;
import com.project.pin.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LivroIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    private Livro livroExistente;
    private Autor autor;

    @BeforeEach
    void setup() {
        livroRepository.deleteAll();
        autorRepository.deleteAll();

        autor = new Autor();
        autor.setNome("Stephen King");
        autor.setSobre("Americano");
        autor = autorRepository.save(autor);

        livroExistente = new Livro();
        livroExistente.setNomeLivro("Uma Familia Feliz");
        livroExistente.setIsbn("ISBN-123456789");
        livroExistente.setSinopse("Uma história sobre uma família que enfrenta desafios inesperados.");
        livroExistente.setPages(320);
        livroExistente.setAnoPublicado(2021);
        livroExistente.setPrecoUnit(59.90);
        livroExistente.setImg("familiafeliz.jpg");
        livroExistente.setAutor(autor);
        livroRepository.save(livroExistente);
    }

    @Test
    @DisplayName("CT03 - Consulta detalhada de livro existente")
    void deveRetornarDetalhamentoLivroExistente() {
        String urlBusca = "/livros/buscar?nomeLivro=Uma Familia Feliz";
        ResponseEntity<Livro[]> respostaBusca = restTemplate.getForEntity(urlBusca, Livro[].class);

        assertThat(respostaBusca.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respostaBusca.getBody()).isNotNull();
        assertThat(respostaBusca.getBody().length).isGreaterThan(0);

        Livro livroRetornado = respostaBusca.getBody()[0];
        String urlDetalhe = "/livros/" + livroRetornado.getId();
        ResponseEntity<Livro> respostaDetalhe = restTemplate.getForEntity(urlDetalhe, Livro.class);

        assertThat(respostaDetalhe.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respostaDetalhe.getBody()).isNotNull();

        Livro detalhado = respostaDetalhe.getBody();

        assertThat(detalhado.getNomeLivro()).isEqualTo("Uma Familia Feliz");
        assertThat(detalhado.getIsbn()).isEqualTo("ISBN-123456789");
        assertThat(detalhado.getPages()).isEqualTo(320);
        assertThat(detalhado.getAnoPublicado()).isEqualTo(2021);
        assertThat(detalhado.getSinopse()).contains("família");
        assertThat(detalhado.getPrecoUnit()).isEqualTo(59.90);
        assertThat(detalhado.getImg()).isEqualTo("familiafeliz.jpg");
    }
}
