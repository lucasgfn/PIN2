package com.project.pin;

import com.project.pin.controller.LivroController;
import com.project.pin.dto.Livro.LivroResponseDTO;
import com.project.pin.service.LivroService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class LivroControllerTest {

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    private LivroResponseDTO umLivro;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        umLivro= new LivroResponseDTO(
                1L,
                "ISBN-123456789",
                "Uma Família",
                "Descrição do livro",
                300,
                2022,
                59.9,
                "/images/livro.jpg",
                1L,
                "Autor Exemplo"
        );
    }

    @Test
    @DisplayName("CT13 - Deve retornar lista de livros quando nome é válido - Lista com Livros")
    public void deveRetornarLivrosQuandoTituloCorreto() {
        LivroResponseDTO livro = new LivroResponseDTO(
                2L,
                "ISBN-123456781",
                "Uma Família Feliz",
                "Descrição do livro",
                300,
                2023,
                59.9,
                "/images/livro.jpg",
                2L,
                "Autor Exemplo 2"
        );

        //Simula que encontro os livros
        when(livroService.buscarPorTitulo("Uma Família"))
                .thenReturn(List.of(umLivro, livro));

        //Pesquisa o livro
        ResponseEntity<List<LivroResponseDTO>> resposta =
                livroController.buscarPorTitulo("Uma Família");

        assertThat(resposta.getStatusCodeValue()).isEqualTo(200);    //Confirma que OK
        assertThat(resposta.getBody()).isNotEmpty();
        assertThat(resposta.getBody()).hasSize(2);

        assertThat(resposta.getBody().get(0).nomeLivro()).isEqualTo("Uma Família");
        assertThat(resposta.getBody().get(1).nomeLivro()).isEqualTo("Uma Família Feliz");

        verify(livroService, times(1)).buscarPorTitulo(eq("Uma Família"));
    }


    @Test
    @DisplayName("CT14 - Busca com dados incorretos - Lista Vazia")
    public void deveRetornarListaVaziaQuandoTituloIncorreto() {
        when(livroService.buscarPorTitulo("Uma Livro"))
                .thenReturn(List.of());

        ResponseEntity<List<LivroResponseDTO>> resposta =
                livroController.buscarPorTitulo("Uma Livro");

        assertThat(resposta.getStatusCodeValue()).isEqualTo(200);
        assertThat(resposta.getBody()).isEmpty();

        verify(livroService, times(1)).buscarPorTitulo("Uma Livro");
    }


    @Test
    @DisplayName("CT15 - Busca sem informar título da obra - Nenhuma obra foi informada")
    public void deveLancarErroQuandoTituloNaoInformado() {
        try {
            livroController.buscarPorTitulo(null);
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Nenhuma obra foi informada");
        }
    }
}
