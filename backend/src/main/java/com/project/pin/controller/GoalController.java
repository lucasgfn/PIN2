package com.project.pin.controller;

import com.project.pin.entity.Comprador;
import com.project.pin.entity.Goal;
import com.project.pin.repository.CompradorRepository;
import com.project.pin.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private CompradorRepository compradorRepository;

    @GetMapping("/paginas/{compradorId}")
    public ResponseEntity<Goal> getQuantidadePaginas(@PathVariable Long compradorId) {
        Optional<Goal> goal = goalRepository.findByCompradorId(compradorId);
        return goal.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/paginas/{compradorId}")
    public ResponseEntity<?> createGoal(
            @PathVariable Long compradorId,
            @RequestBody Goal goalRequest
    ) {
        Comprador comprador = compradorRepository.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Comprador não encontrado"));

        if (goalRequest.getQuantidadePaginas() == null) {
            return ResponseEntity.badRequest().body("Campo quantidade de páginas não preenchido");
        }

        Optional<Goal> existingGoal = goalRepository.findByCompradorId(compradorId);
        Goal goal = existingGoal.orElse(new Goal());

        goal.setQuantidadePaginas(goalRequest.getQuantidadePaginas());
        goal.setDiasLidos(goalRequest.getDiasLidos());
        goal.setComprador(comprador);

        Goal savedGoal = goalRepository.save(goal);
        return ResponseEntity.status(201).body("Meta criada com sucesso!");

    }


    @PostMapping("/dias/{compradorId}")
    public ResponseEntity<Goal> createOrUpdateDiasLidos(
            @PathVariable Long compradorId,
            @RequestBody List<String> dias
    ) {
        Comprador comprador = compradorRepository.findById(compradorId)
                .orElseThrow(() -> new RuntimeException("Comprador não encontrado"));

        Optional<Goal> optionalGoal = goalRepository.findByCompradorId(compradorId);
        Goal goal = optionalGoal.orElse(new Goal());

        goal.setDiasLidos(dias);
        if (goal.getQuantidadePaginas() == null) {
            goal.setQuantidadePaginas(0);
        }
        goal.setComprador(comprador);

        Goal savedGoal = goalRepository.save(goal);
        return ResponseEntity.ok(savedGoal);
    }
    
    @GetMapping("/dias/{compradorId}")
    public ResponseEntity<List<String>> getDiasLidos(@PathVariable Long compradorId) {
        Optional<Goal> goal = goalRepository.findByCompradorId(compradorId);
        return goal.map(g -> ResponseEntity.ok(g.getDiasLidos()))
                .orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @PutMapping("/dias/{goalId}")
    public ResponseEntity<?> updateDiasLidos(
            @PathVariable Long goalId,
            @RequestBody List<String> dias
    ) {
        return goalRepository.findById(goalId)
                .map(goal -> {
                    goal.getDiasLidos().clear();
                    goal.getDiasLidos().addAll(dias);
                    goalRepository.save(goal);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
