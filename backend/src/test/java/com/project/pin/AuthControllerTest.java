package com.project.pin;

import com.project.pin.entity.Comprador;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.service.AuthCompradorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthCompradorService authCompradorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("CT10 - Deve autenticar comprador com credenciais corretas")
    public void deveAutenticarCompradorComSucesso() {
        Comprador comprador = new Comprador();
        comprador.setUsername("clarabecker");
        comprador.setPassword("senhaCodificada");

        Mockito.when(compradorRepository.findByUsername("clarabecker"))
                .thenReturn(Optional.of(comprador));

        Mockito.when(passwordEncoder.matches("minhaSenha123", "senhaCodificada"))
                .thenReturn(true);

        Comprador resultado = authCompradorService.autenticar("clarabecker", "minhaSenha123");

        assertNotNull(resultado);
        assertEquals("clarabecker", resultado.getUsername());
    }

    @Test
    @DisplayName("CT11 - Não deve autenticar comprador com senha incorreta")
    public void naoDeveAutenticarComSenhaIncorreta() {
        Comprador comprador = new Comprador();
        comprador.setUsername("clarabecker");
        comprador.setPassword("senhaCodificada");

        Mockito.when(compradorRepository.findByUsername("clarabecker"))
                .thenReturn(Optional.of(comprador));

        Mockito.when(passwordEncoder.matches("senhaErrada", "senhaCodificada"))
                .thenReturn(false);

        Comprador resultado = authCompradorService.autenticar("clarabecker", "senhaErrada");

        assertNull(resultado);
    }

    @Test
    @DisplayName("CT12 - Não deve autenticar comprador inexistente")
    public void naoDeveAutenticarUsuarioInexistente() {
        Mockito.when(compradorRepository.findByUsername("naoExiste"))
                .thenReturn(Optional.empty());

        Comprador resultado = authCompradorService.autenticar("naoExiste", "qualquerSenha");

        assertNull(resultado);
    }
}
