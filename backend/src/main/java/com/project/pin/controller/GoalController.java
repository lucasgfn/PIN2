package com.project.pin.controller;

import com.project.pin.entity.Goal;
import com.project.pin.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/goals")
@CrossOrigin(origins = "*") // libere o front-end
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    @GetMapping("/paginas")
    public ResponseEntity<Goal> getQuantidadePaginas() {
        return goalRepository.findAll().stream()
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/paginas")
    public Goal createGoal(@RequestBody Goal goal) {
        return goalRepository.save(goal);
    }

    @GetMapping("/dias")
    public ResponseEntity<List<String>> getDiasLidos() {
        return goalRepository.findAll().stream()
                .findFirst()
                .map(goal -> ResponseEntity.ok(goal.getDiasLidos()))
                .orElse(ResponseEntity.ok(Collections.emptyList()));
    }

    @PutMapping("/dias/{id}")
    public ResponseEntity<?> updateDiasLidos(@PathVariable Long id, @RequestBody List<String> dias) {
        return goalRepository.findById(id)
                .map(goal -> {
                    goal.setDiasLidos(dias);
                    goalRepository.save(goal);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
