package com.project.pin.integra;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.repository.CompradorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompradorManterTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Comprador comprador;

    @BeforeEach
    void setup() {
        compradorRepository.deleteAll();

        comprador = new Comprador();
        comprador.setNome("Clara Becker");
        comprador.setEmail("clara.becker@email.com");
        comprador.setCpf("123.456.789-00");
        comprador.setUsername("clarabecker");
        comprador.setPassword(passwordEncoder.encode("senhaAntiga"));
        comprador.setRua("Rua Velha, 88");
        comprador.setTelefone("(48) 98888-0000");
        comprador.setBairro("Centro");
        comprador.setCidade("Florianópolis");
        comprador.setCep("88000-000");
        comprador.setEstado("SC");
        comprador.setImg("perfil_clara.jpg");
        comprador.setNivel(1);
        comprador.setRecebeuDesconto(false);

        comprador = compradorRepository.save(comprador);
    }

    @Test
    @DisplayName("CT07/CT08 - Deve autenticar comprador com nova senha - Updated e Login com Sucesso")
    void deveAutenticarComDadosAtualizados() {

        Long id = comprador.getId();

        String updateJson = """
        {
            "id": %d,
            "nome": "Clara Becker",
            "email": "clara.becker@email.com",
            "cpf": "123.456.789-00",
            "username": "clarabecker",
            "senha": "novaSenha123",
            "rua": "Rua Velha, 88",
            "telefone": "(48) 98888-0000",
            "bairro": "Centro",
            "cidade": "Florianópolis",
            "cep": "88000-000",
            "estado": "SC",
            "img": "perfil_clara.jpg",
            "nivel": 1,
            "recebeuDesconto": false
        }
        """.formatted(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> uprequest = new HttpEntity<>(updateJson, headers);

        ResponseEntity<String> updateResposta = restTemplate.exchange(
                "/compradores/atualizar/" + id,
                HttpMethod.PUT,
                uprequest,
                String.class
        );

        assertThat(updateResposta.getStatusCode()).isEqualTo(HttpStatus.OK);


        String loginJson = """
                    {
                        "username": "clarabecker",
                        "password": "novaSenha123"
                    }
                """;

        HttpEntity<String> request = new HttpEntity<>(loginJson, headers);

        ResponseEntity<String> loginResposta = restTemplate.postForEntity(
                "/auth/login",
                request,
                String.class
        );

        assertThat(loginResposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResposta.getBody()).contains("Login bem-sucedido!");
    }
}