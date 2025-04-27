package com.project.pin.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_autor")
    private Long id;
    @Column(name = "nome_autor")
    private String nome;
    @Column(name = "sobre")
    private String sobre;
    @Column(name = "img")
    private String img;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> listLivros;


}
