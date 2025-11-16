package com.project.pin.integra;

import com.project.pin.entity.Comprador;
import com.project.pin.entity.Goal;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.repository.GoalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GoalIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private GoalRepository goalRepository;

    private Comprador comprador;

    @BeforeEach
    void setup() {
        goalRepository.deleteAll();
        compradorRepository.deleteAll();

        comprador = new Comprador();
        comprador.setNome("Lucas");
        comprador.setEmail("lucas@email.com");
        comprador.setCpf("12345678900");
        comprador.setUsername("lucas123");
        comprador.setPassword("senhaSegura123");
        comprador.setTelefone("4899999999");
        comprador.setRua("Rua A");
        comprador.setBairro("Centro");
        comprador.setCidade("Florianópolis");
        comprador.setCep("88000-000");
        comprador.setEstado("SC");

        comprador = compradorRepository.save(comprador);
    }

    @Test
    @DisplayName("CT05 - Salvar meta diária com quantidade de páginas e dias da semana")
    void deveSalvarMetaDiariaComSucesso() {

        String json = """
            {
              "quantidadePaginas": 15,
              "diasLidos": ["Segunda", "Sexta", "Sabado"]
            }
            """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> resposta = restTemplate.postForEntity(
                "/goals/paginas/" + comprador.getId(),
                request,
                String.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody()).isEqualTo("Meta criada com sucesso!");

        // Verificação no banco
        Goal meta = goalRepository.findByCompradorId(comprador.getId()).orElse(null);
        assertThat(meta).isNotNull();

        assertThat(meta.getQuantidadePaginas()).isEqualTo(15);
        assertThat(meta.getDiasLidos())
                .containsExactlyInAnyOrder("Segunda", "Sexta", "Sabado");

        assertThat(meta.getComprador().getId()).isEqualTo(comprador.getId());
    }
}
