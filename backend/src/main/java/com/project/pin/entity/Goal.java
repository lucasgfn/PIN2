package com.project.pin.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.pin.entity.Comprador;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comprador_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Comprador comprador;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "goal_metas_mensais", joinColumns = @JoinColumn(name = "goal_id"))
    @Column(name = "livro")
    private List<String> metasMensais = new ArrayList<>();

}
