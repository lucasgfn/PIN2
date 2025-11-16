package com.project.pin.integra;


import com.project.pin.entity.Comprador;
import com.project.pin.repository.CompradorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Transactional
public class CompradorCadastroTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CompradorRepository compradorRepository;
    private Comprador comprador;

    @BeforeEach
    void setup() {
        compradorRepository.deleteAll();

        comprador =  new Comprador();
        comprador.setNome("Lucas Falcade");
        comprador.setEmail("lucas.falcade@email.com");
        comprador.setCpf("123.456.789-11");
        comprador.setUsername("lucasfalcade");
        comprador.setPassword("senhaSegura123");
        comprador.setRua("Rua das Flores, 120");
        comprador.setTelefone("(48) 99999-8888");
        comprador.setBairro("Bela Vista");
        comprador.setCidade("Ibirama");
        comprador.setCep("88000-000");
        comprador.setEstado("SC");
        comprador.setImg("perfil_lucas.jpg");
        comprador.setNivel(1);
        comprador.setRecebeuDesconto(false);
    }

    @Test
    @DisplayName("CT01 - Deve cadastrar comprador com sucesso - Cadastro com Sucesso")
    public void deveCadastrarCompradorComSucesso() {
        ResponseEntity<Comprador> resposta = restTemplate.postForEntity(
                "/compradores",
                comprador,
                Comprador.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getEmail()).isEqualTo("lucas.falcade@email.com");
        assertThat(resposta.getBody().getCpf()).isEqualTo("123.456.789-11");
        assertThat(resposta.getBody().getTelefone()).isEqualTo("(48) 99999-8888");
        assertThat(resposta.getBody().getCep()).isEqualTo("88000-000");

        Comprador salvo = compradorRepository.findByUsername("lucasfalcade").orElse(null);
        assertThat(salvo).isNotNull();
        assertThat(salvo.getEmail()).isEqualTo("lucas.falcade@email.com");
    }

    @Test
    @DisplayName("CT02 - Deve autenticar comprador com sucesso após cadastro - Login bem-sucedido!")
    public void deveAutenticarCompradorComSucesso() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonLogin = """
            {
                "username": "lucasfalcade",
                "password": "senhaSegura123"
            }
        """;

        HttpEntity<String> request = new HttpEntity<>(jsonLogin, headers);

        ResponseEntity<String> resposta = restTemplate.postForEntity(
                "/auth/login",
                request,
                String.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).contains("Login bem-sucedido!");
    }

}
