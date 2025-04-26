package com.project.pin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    protected Long id;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "email")
    protected String email;
    @Column(name = "cpf")
    protected String cpf;
    @Column(name = "username")
    protected String username;
    @Column(name = "password")
    protected String password;


}
