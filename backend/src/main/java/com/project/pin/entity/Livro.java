package com.project.pin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "livro")
public class Livro {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_livro")
    private Long id;
    @Column(name = "isbn")
    private String ibsn;
    @Column(name = "nomeLivro")
    private String nomeLivro;
    @Column(name = "sinopse")
    private String sinopse;
    @Column(name = "pages")
    private int pages;
    @Column(name = "anoPublicado")
    private int anoPublicado;
    @Column(name = "precoUnit")
    private double precoUnit;
    @Column(name = "image", length = 1000)
    private String img;

    @OneToMany(mappedBy = "livro")
    @JsonIgnore
    private List<ItemCompra> itensCompra;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
}
