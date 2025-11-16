package com.project.pin.extras;

import com.project.pin.controller.MonthGoalController;
import com.project.pin.entity.Comprador;
import com.project.pin.entity.Goal;
import com.project.pin.repository.GoalRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MonthGoalControllerTest {
    @Mock
    private GoalRepository goalRepository;

    @InjectMocks
    private MonthGoalController goalController;

    private Comprador comprador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        comprador = new Comprador();
        comprador.setId(1L);
        comprador.setNome("Lucas Falcade");
        comprador.setEmail("lucas@email.com");
        comprador.setCpf("12345678900");
        comprador.setUsername("lucasf");
        comprador.setPassword("senhaSegura123");
    }

    @Test
    @DisplayName("CT16 - Criação de meta de leitura mensal - Meta criada")
    void deveCriarMetaDeLeituraMensalComSucesso() {
        Goal novaMeta = new Goal();
        novaMeta.setId(1L);
        novaMeta.setComprador(comprador);
        novaMeta.setQuantidadePaginas(4);
        novaMeta.setDiasLidos(List.of("Segunda", "Terca"));
        novaMeta.setMetasMensais(List.of("A paixão segundo G.H"));

        when(goalRepository.save(novaMeta)).thenReturn(novaMeta);

        Goal resposta = goalController.createGoal(novaMeta);
        assertThat(resposta).isNotNull();

        assertThat(resposta.getMetasMensais()).contains("A paixão segundo G.H");
        verify(goalRepository, times(1)).save(novaMeta);
    }

    @Test
    @DisplayName("CT17 - Meta de leitura mensal com dado faltante - Meta não salva/Campo não preenchico")
    void deveFalharAoCriarMetaComTituloVazio() {
        Goal metaInvalida = new Goal();
        metaInvalida.setMetasMensais(List.of(""));

        Exception excecao = assertThrows(IllegalArgumentException.class, () -> {
            goalController.createGoal(metaInvalida);
        });

        assertThat(excecao.getMessage()).contains("Campo não preenchido");
        verify(goalRepository, never()).save(any());
    }

}