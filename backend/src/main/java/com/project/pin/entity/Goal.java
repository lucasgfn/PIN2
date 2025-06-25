package com.project.pin.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantidadePaginas;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "goal_dias_lidos", joinColumns = @JoinColumn(name = "goal_id"))
    @Column(name = "dia")
    private List<String> diasLidos = new ArrayList<>();

}
