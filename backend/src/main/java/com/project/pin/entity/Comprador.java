package com.project.pin.entity;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.lang.NonNull;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue("COMPRADOR")
public class Comprador extends Usuario{
    @NotBlank(message = "O campo (telefone) não pode estar vazio")
    @Column(name = "telefone") @NonNull
    private String telefone;
    @Column(name = "rua")
    private String rua;
    @Column(name = "bairro")
    private String bairro;
    @Column(name = "cidade")
    private String cidade;
    @Column(name = "cep")
    private String cep;
    @Column(name = "estado")
    private String estado;
    @Column(name = "img", columnDefinition = "TEXT")
    private String img;
    @Column(name = "nivel")
    private int nivel = 1;
    @Column(name = "desconto")
    private boolean recebeuDesconto = false;

    @OneToMany(mappedBy = "comprador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Compra> listCompras;
}
