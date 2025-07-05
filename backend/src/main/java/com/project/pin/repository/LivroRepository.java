package com.project.pin.repository;

import com.project.pin.entity.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByNomeLivroContainingIgnoreCase(String nomeLivro);

}
