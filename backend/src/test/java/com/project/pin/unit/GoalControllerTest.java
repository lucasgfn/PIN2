package com.project.pin.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pin.controller.GoalController;
import com.project.pin.entity.Comprador;
import com.project.pin.entity.Goal;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.repository.GoalRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class GoalControllerTest {
    @Mock
    private GoalRepository goalRepository;

    @Mock
    private CompradorRepository compradorRepository;

    @InjectMocks
    private GoalController goalController;

    private Comprador comprador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comprador = new Comprador();
        comprador.setId(1L);
        comprador.setNome("Lucas");
    }


    @Test
    @DisplayName("CT18 - Criação de meta de leitura diária - Meta criada com sucesso")
    void deveCriarMetaDeLeituraDiariaComSucesso() {
        Goal goalRequest = new Goal();
        goalRequest.setQuantidadePaginas(15);
        goalRequest.setDiasLidos(List.of("Segunda", "Terça", "Quarta", "Quinta", "Sexta"));

        when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));
        when(goalRepository.findByCompradorId(1L)).thenReturn(Optional.empty());
        when(goalRepository.save(any(Goal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> resposta = goalController.createGoal(1L, goalRequest);

        assertThat(resposta.getStatusCodeValue()).isEqualTo(201);
        assertThat(resposta.getBody()).isEqualTo("Meta criada com sucesso!");
        verify(goalRepository, times(1)).save(any(Goal.class));
    }


    @Test
    @DisplayName("CT19 - Criação de meta de leitura diária sem quantidade de páginas - Qnt de páginas não preenchidon")
    void deveFalharAoCriarMetaSemQuantidadeDePaginas() {
        Goal goalInvalido = new Goal();
        goalInvalido.setQuantidadePaginas(null);
        goalInvalido.setDiasLidos(List.of("Segunda", "Terça"));

        when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));

        ResponseEntity<?> resposta = goalController.createGoal(1L, goalInvalido);

        assertThat(resposta.getStatusCodeValue()).isEqualTo(400);
        assertThat(resposta.getBody()).isEqualTo("Campo quantidade de páginas não preenchido");
        verify(goalRepository, never()).save(any(Goal.class));
    }


    @Test
    @DisplayName("CT20 - Criação de meta de leitura diária sem dias selecionados - Salvo")
    void deveCriarMetaSemDiasSelecionados() {
        Goal goalRequest = new Goal();
        goalRequest.setQuantidadePaginas(15);
        goalRequest.setDiasLidos(List.of());

        when(compradorRepository.findById(1L)).thenReturn(Optional.of(comprador));
        when(goalRepository.findByCompradorId(1L)).thenReturn(Optional.empty());
        when(goalRepository.save(any(Goal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<?> resposta = goalController.createGoal(1L, goalRequest);

        assertThat(resposta.getStatusCodeValue()).isEqualTo(201);
        assertThat(resposta.getBody()).isEqualTo("Meta criada com sucesso!");
        verify(goalRepository, times(1)).save(any(Goal.class));
    }
}