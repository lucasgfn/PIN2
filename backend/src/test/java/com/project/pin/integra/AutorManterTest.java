package com.project.pin.integra;

import com.project.pin.entity.Autor;
import com.project.pin.repository.AutorRepository;
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
public class AutorManterTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AutorRepository autorRepository;

    @BeforeEach
    void setup() {
        autorRepository.deleteAll();
    }

    @Test
    @DisplayName("CT09/CT10 – Cadastrar e consultar autor com sucesso")
    public void deveCadastrarEConsultarAutor() {

        String json = """
            {
                "nome": "Stephen Edwin King",
                "sobre": "Stephen Edwin King (Portland, 21 de setembro de 1947) é um escritor norte-americano de terror, ficção sobrenatural, suspense, ficção científica e fantasia. Seus livros já venderam mais de 400 milhões de cópias, com publicações em mais de 40 países. É o 9º autor mais traduzido no mundo",
                "img": "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR-VJNoYJFEVOwKGX5j5S07tiR_VayOp4uGviLxfrthf-W9FuajiZ4K8KpFf_g_L1F769YpOZve7zp_xH58LYeGcDunGauKyFB_5mRqjswW"
            }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> cadastroRequest = new HttpEntity<>(json, headers);

       // CT09 - Cadastrar Autor
        ResponseEntity<Autor> cadastroResp = restTemplate.postForEntity(
                "/autores",
                cadastroRequest,
                Autor.class
        );

        assertThat(cadastroResp.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Long idGerado = cadastroResp.getBody().getId();
        assertThat(idGerado).isNotNull();


        // CT10 = Consultar Autor
        ResponseEntity<String> consultaResp = restTemplate.getForEntity(
                "/autores/" + idGerado,
                String.class
        );

        assertThat(consultaResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        String body = consultaResp.getBody();

        assertThat(body).contains("Stephen Edwin King");
        assertThat(body).contains("\"listLivros\":[]");
    }
}
