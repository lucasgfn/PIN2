package com.project.pin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "O campo ISBN é obrigatório")
    private String isbn;
    @Column(name = "nomeLivro")
    @NotBlank(message = "O campo (Nome do Livro) é obrigatório")
    private String nomeLivro;
    @Column(name = "sinopse")
    private String sinopse;
    @Column(name = "pages")
    @Min(value = 1, message = "O livro deve ter ao menos 1 página")
    private int pages;
    @Column(name = "anoPublicado")
    @Min(value = 1000, message = "Ano de publicação inválido")
    @Max(value = 9999, message = "Ano de publicação inválido")
    private int anoPublicado;
    @Column(name = "precoUnit")
    @Positive(message = "O preço do livro deve ser positivo")
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
