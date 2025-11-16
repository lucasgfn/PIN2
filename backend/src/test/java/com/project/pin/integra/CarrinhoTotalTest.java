package com.project.pin.integra;

import com.project.pin.entity.Autor;
import com.project.pin.entity.Livro;
import com.project.pin.entity.Comprador;
import com.project.pin.repository.AutorRepository;
import com.project.pin.repository.LivroRepository;
import com.project.pin.repository.CompradorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarrinhoTotalTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    @Qualifier("passwordEncoder")
    private PasswordEncoder encoder;

    private HttpHeaders headers;
    private Livro livro1;
    private Livro livro2;

    @BeforeEach
    public void setup() {
        compradorRepository.deleteAll();
        livroRepository.deleteAll();
        autorRepository.deleteAll();


        Comprador c = new Comprador();
        c.setNome("Lucas Tester");
        c.setEmail("lucas@test.com");
        c.setCpf("123.456.789-00");
        c.setTelefone("123456789");
        c.setUsername("lucas");
        c.setPassword(encoder.encode("senhaSegura123"));
        compradorRepository.save(c);


        Autor autor = new Autor();
        autor.setNome("Raphael Montes");
        autor.setSobre("Romancista policial brasileiro. Obra mais conhecida: Dias Perfeitos.");
        autor.setImg("https://ogimg.infoglobo.com.br/cultura/livros/23756501-f2f-604/FT1086A/Raphael-Montes.jpg");
        autorRepository.save(autor);


        livro1 = new Livro();
        livro1.setNomeLivro("Livro 1");
        livro1.setIsbn("ISBN-1111");
        livro1.setPrecoUnit(50.0);
        livro1.setAnoPublicado(2020);
        livro1.setPages(200);
        livro1.setAutor(autor);
        livroRepository.save(livro1);

        livro2 = new Livro();
        livro2.setNomeLivro("Livro 2");
        livro2.setIsbn("ISBN-2222");
        livro2.setPrecoUnit(59.8);
        livro2.setAnoPublicado(2021);
        livro2.setPages(250);
        livro2.setAutor(autor);
        livroRepository.save(livro2);

        // Autenticação
        String loginJson = """
                {
                    "username": "lucas",
                    "password": "senhaSegura123"
                }
                """;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> loginResp = restTemplate.postForEntity(
                "/auth/login",
                new HttpEntity<>(loginJson, headers),
                String.class
        );

        assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Manter a sessão
        String sessionCookie = loginResp.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
        headers.add(HttpHeaders.COOKIE, sessionCookie);
    }

    @Test
    @DisplayName("CT14/CT15 – Adicionar livros e calcular total do carrinho")
    public void deveAdicionarLivrosECalcularTotal() {

        // CT14 – Adicionar Livros
        String addLivro1 = """
                {
                    "idLivro": %d,
                    "quantidade": 1
                }
                """.formatted(livro1.getId());

        ResponseEntity<String> resp1 = restTemplate.postForEntity(
                "/carrinho/add",
                new HttpEntity<>(addLivro1, headers),
                String.class
        );
        assertThat(resp1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp1.getBody()).contains("Livro 1");

        String addLivro2 = """
                {
                    "idLivro": %d,
                    "quantidade": 2
                }
                """.formatted(livro2.getId());

        ResponseEntity<String> resp2 = restTemplate.postForEntity(
                "/carrinho/add",
                new HttpEntity<>(addLivro2, headers),
                String.class
        );
        assertThat(resp2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resp2.getBody()).contains("Livro 2");

        // CT15 – Consultar total do carrinho
        ResponseEntity<String> totalResp = restTemplate.exchange(
                "/carrinho/total",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        assertThat(totalResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Valor esperado: 1*50 + 2*59.8 = 169.6
        assertThat(totalResp.getBody()).contains("169.6");
    }
}
