package com.project.pin.controller;

import com.project.pin.entity.Comprador;
import com.project.pin.entity.Goal;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/monthgoals")
public class MonthGoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    @GetMapping("/{compradorId}")
    public Optional<Goal> getGoalByComprador(@PathVariable Long compradorId) {
        return goalRepository.findByCompradorId(compradorId);
    }


    @PostMapping
    public Goal createGoal(@RequestBody Goal requestGoal) {
        Long compradorId = requestGoal.getComprador().getId();
        Comprador compradorCompleto = compradorRepository.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Comprador não encontrado"));

        // Cria um Goal REAL com dados consistentes
        Goal goal = new Goal();
        goal.setComprador(compradorCompleto);
        goal.setMetasMensais(requestGoal.getMetasMensais());
        goal.setDiasLidos(requestGoal.getDiasLidos());
        goal.setQuantidadePaginas(requestGoal.getQuantidadePaginas());

        return goalRepository.save(goal);

    }

    @PutMapping("/{goalId}/monthgoals")
    public Goal updateMetasMensais(@PathVariable Long goalId, @RequestBody List<String> metasMensais) {
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new RuntimeException("Goal não encontrada"));
        goal.setMetasMensais(metasMensais);
        return goalRepository.save(goal);
    }

    @DeleteMapping("/{goalId}/monthgoals")
    public Goal deleteMetaMensal(
            @PathVariable Long goalId,
            @RequestParam String livro
    ) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new RuntimeException("Goal não encontrada"));

        goal.getMetasMensais().removeIf(meta -> meta.equalsIgnoreCase(livro));
        return goalRepository.save(goal);
    }

}
