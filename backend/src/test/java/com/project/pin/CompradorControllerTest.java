package com.project.pin;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.exceptions.UserFoundException;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.service.CompradorService;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.Validator;

public class CompradorControllerTest {
    @InjectMocks
    private CompradorService compradorService;
    @Mock
    private CompradorRepository compradorRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    private Comprador comprador;
    private Validator validator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        comprador = new Comprador();
        comprador.setId(1L);
        comprador.setNome("Clara Becker");
        comprador.setEmail("clara.becker@email.com");
        comprador.setCpf("123.456.789-00");
        comprador.setUsername("clarabecker");
        comprador.setPassword("senhaCodificada");
        comprador.setRua("Rua das Flores, 120");
        comprador.setTelefone("(48) 99999-8888");
        comprador.setBairro("Centro");
        comprador.setCidade("Florianópolis");
        comprador.setCep("88000-000");
        comprador.setEstado("SC");
        comprador.setImg("perfil_clara.jpg");
    }

    @Test
    @DisplayName("CT01 - Valida se o sistema efetua o cadastro de um usuário de forma correta")
    public void deveCadastrarCompradorComSucesso() {
        when(compradorRepository.findByCpfOrUsername(anyString(), anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCriptografada");
        when(compradorRepository.save(any(Comprador.class))).thenReturn(comprador);

        Comprador resultado = compradorService.cadastrarComprador(comprador);

        assertNotNull(resultado);
        assertEquals("Clara Becker", resultado.getNome());
        verify(compradorRepository, times(1)).save(any(Comprador.class));
    }

    @Test
    @DisplayName("CT02 - Valida se o sistema impede a criação de usuários com campos vazios ")
    public void deveLancarExcecaoSeCamposObrigatoriosVazios() {
        Comprador compradorInvalido = new Comprador();
        compradorInvalido.setNome("Clara Becker");
        compradorInvalido.setEmail("clara.becker@email.com");
        compradorInvalido.setCpf(" ");
        compradorInvalido.setUsername(" ");
        compradorInvalido.setPassword(" ");

        Exception excecao = assertThrows(IllegalArgumentException.class, () -> {
            compradorService.cadastrarComprador(compradorInvalido);
        });

        assertEquals("Campos obrigatórios devem ser preenchidos", excecao.getMessage());
        verify(compradorRepository, never()).save(any());
    }

    @Test
    @DisplayName("CT03 - Deve lançar exceção se e-mail for inválido")
    public void deveLancarExcecaoSeEmailForInvalido() {
        Comprador compradorInvalido = new Comprador();
        compradorInvalido.setNome("Clara Becker");
        compradorInvalido.setEmail("clara.becker@");
        compradorInvalido.setCpf("123.456.789-00");
        compradorInvalido.setUsername("clarabecker");
        compradorInvalido.setPassword("minhaSenha123");

        Exception excecao = assertThrows(IllegalArgumentException.class, () -> {
            compradorService.cadastrarComprador(compradorInvalido);
        });

        assertEquals("O e-mail informado é inválido", excecao.getMessage());
        verify(compradorRepository, never()).save(any());
    }

    @Test
    @DisplayName("CT04/CT05 - Deve lançar exceção ao cadastrar comprador com CPF ou username já existentes")
    public void deveLancarExcecaoSeCpfOuUsernameJaExistirem() {
        when(compradorRepository.findByCpfOrUsername(anyString(), anyString()))
                .thenReturn(Optional.of(comprador));

        assertThrows(UserFoundException.class, () -> compradorService.cadastrarComprador(comprador));
    }

    @Test
    @DisplayName("CT06 - Verifica se o sistema impede a criação de um usuário com uma senha menor que 10 caracteres")
    public void deveInvalidarSenhaCurta() {
        Comprador comprador = new Comprador();
        comprador.setNome("Clara Becker");
        comprador.setEmail("clara.becker@email.com");
        comprador.setCpf("123.456.789-00");
        comprador.setUsername("clarabecker");
        comprador.setRua("Rua das Flores, 120");
        comprador.setTelefone("(48) 99999-8888");
        comprador.setBairro("Centro");
        comprador.setCidade("Florianópolis");
        comprador.setCep("88000-000");
        comprador.setEstado("SC");
        comprador.setImg("perfil_clara.jpg");
        comprador.setPassword("123456789");

        var violations = validator.validate(comprador);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().contains("A senha deve conter entre 10 e 200 caracteres"));
    }

    @Test
    @DisplayName("CT07 - Verifica se o sistema impede a criação de um usuário com uma senha maior que 200 caracteres")
    public void deveInvalidarSenhaLonga() {
        Comprador comprador = new Comprador();
        comprador.setNome("Clara Becker");
        comprador.setEmail("clara.becker@email.com");
        comprador.setCpf("123.456.789-00");
        comprador.setUsername("clarabecker");
        comprador.setRua("Rua das Flores, 120");
        comprador.setTelefone("(48) 99999-8888");
        comprador.setBairro("Centro");
        comprador.setCidade("Florianópolis");
        comprador.setCep("88000-000");
        comprador.setEstado("SC");
        comprador.setImg("perfil_clara.jpg");
        comprador.setPassword("g@5!jPq$kE#sW3*rD2&yHn@9bV6^cM1fL(7zJq!sP4*gT2)kE#sW3*rD2&yHn@9bV6^cM1fL(7zJq!sP4*gT2)kE#sW3*rD2&yHn@9bV6^cM1fL(7zJq!sP4*gT2)kE#sW3*rD2&yHn@9bV6^cM1fL(7zJq!sP4*gT2)kE#sW3*rD2&yHncM1fL(7zJq!sP4*gT2)kE#1");

        var violations = validator.validate(comprador);

        assertThat(violations)
                .anyMatch(v -> v.getMessage().contains("A senha deve conter entre 10 e 200 caracteres"));
    }


    @Test
    @DisplayName("CT08 - Deve atualizar campos do comprador corretamente")
    public void deveAtualizarCompradorComSucesso() {
        when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));
        when(compradorRepository.save(any(Comprador.class))).thenReturn(comprador);

        CompradorRequestDTO dto = new CompradorRequestDTO(
                1L,
                "Clara Becker Atualizada",
                "clara.novo@email.com",
                "123.456.789-00",
                "clarabecker22",
                "novaSenha123",
                "Rua Nova, 321",
                "(48) 98888-7777",
                "Centro",
                "Florianópolis",
                "88000-000",
                "SC",
                "nova_img.jpg",
                1,
                false
        );

        when(passwordEncoder.encode(anyString())).thenReturn("senhaCodificadaNova");

        Comprador atualizado = compradorService.updateComprador(1L, dto);

        assertEquals("Clara Becker Atualizada", atualizado.getNome());
        assertEquals("clarabecker22", atualizado.getUsername());
        assertEquals("senhaCodificadaNova", atualizado.getPassword());
        verify(compradorRepository).save(any(Comprador.class));
    }

    @Test
    @DisplayName("CT09 - Deve lançar exceção se DTO for nulo ao atualizar comprador")
    public void deveLancarExcecaoSeDtoForNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> compradorService.updateComprador(1L, null));
    }
}