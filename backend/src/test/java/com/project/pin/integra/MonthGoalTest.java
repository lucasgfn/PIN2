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


import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MonthGoalTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    private Comprador comprador;

    @BeforeEach
    void setUp() {
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
    @DisplayName("CT04 - Deve salvar meta mensal vinculada ao comprador - Goal Salvo no Banco")
    void deveSalvarMetaMensalVinculadaAoComprador() {

        String json = """
        {
          "comprador": {
            "id": 1
          },
          "metasMensais": [
            "Uma Familia Feliz"
          ]
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<Goal> response = testRestTemplate.exchange(
                "/monthgoals",
                HttpMethod.POST,
                request,
                Goal.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Goal goal = response.getBody();
        assertNotNull(goal);

        assertEquals(comprador.getId(), goal.getComprador().getId());
        assertTrue(goal.getMetasMensais().contains("Uma Familia Feliz"));

        List<Goal> listMetas = goalRepository.findAll();
        assertEquals(1, listMetas.size());
    }
}