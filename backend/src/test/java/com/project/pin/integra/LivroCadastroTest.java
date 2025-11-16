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
public class LivroCadastroTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    private Autor autor;

    @BeforeEach
    public void setup() {
        livroRepository.deleteAll();
        autorRepository.deleteAll();

        autor = new Autor();
        autor.setNome("Andrea Killmore");
        autor.setSobre("Autora de romances policiais");
        autor = autorRepository.save(autor);
    }

    @Test
    @DisplayName("CT06 - Cadastro de livro com autor válido")
    public void deveCadastrarLivroComSucesso() {

        String json = """
        {
          "nomeLivro": "Bom dia, Verônica",
          "isbn": "ISBN-978-6559211043",
          "sinopse": "Verônica Torres trabalha no Departamento de Homicídios e de Proteção à Pessoa, da Polícia Civil em São Paulo. É secretária de Carvana, um delegado pouco confiável, e filha de um respeitado policial, que teve um fim trágico e não totalmente esclarecido.",
          "pages": 320,
          "anoPublicado": 2022,
          "precoUnit": 52.40,
          "img": "https://m.media-amazon.com/images/I/61k-lrleRAL._SY522_.jpg",
          "autorId": %d
        }
        """.formatted(autor.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/livros",
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(livroRepository.count()).isEqualTo(1);

        Livro livro = livroRepository.findAll().get(0);

        assertThat(livro.getNomeLivro()).isEqualTo("Bom dia, Verônica");
        assertThat(livro.getIsbn()).isEqualTo("ISBN-978-6559211043");
        assertThat(livro.getPages()).isEqualTo(320);
        assertThat(livro.getAnoPublicado()).isEqualTo(2022);
        assertThat(livro.getPrecoUnit()).isEqualTo(52.40);
        assertThat(livro.getSinopse()).contains("Verônica Torres trabalha no Departamento de Homicídios");
        assertThat(livro.getImg()).contains("m.media-amazon.com");
        assertThat(livro.getAutor()).isNotNull();
        assertThat(livro.getAutor().getNome()).isEqualTo("Andrea Killmore");
    }
}
