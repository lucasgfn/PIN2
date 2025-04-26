package com.project.pin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Compra {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long id_compra;
    @Column(name = "dataCompra")
    private Date dataCompra;
    @Column(name = "total")
    private double totalCompra;

    @ManyToOne
    @JoinColumn(name = "id_comprador", nullable = false)
    private Comprador comprador;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCompra> itensCompra;


}
