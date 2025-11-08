package com.project.pin.controller;

import com.project.pin.dto.Livro.LivroRequestDTO;
import com.project.pin.dto.Livro.LivroResponseDTO;
import com.project.pin.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponseDTO> save(@RequestBody LivroRequestDTO livroRequestDTO){
        LivroResponseDTO livroResponseDTO = livroService.cadastrarLivro(livroRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroResponseDTO);
    }

    @GetMapping
    public List<LivroResponseDTO> getAll(){
            return livroService.getAll();
        }

    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> getById(@PathVariable Long id){
            return livroService.getById(id);
        }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
            return livroService.deletar(id);
        }

    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> update(@PathVariable Long id, @RequestBody LivroRequestDTO livroRequestDTO){
        return livroService.updateLivro(id, livroRequestDTO);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<LivroResponseDTO>> buscarPorTitulo(@RequestParam String nomeLivro) {
        if (nomeLivro == null || nomeLivro.trim().isEmpty()) {
            throw new IllegalArgumentException("Nenhuma obra foi informada");
        }

        List<LivroResponseDTO> livros = livroService.buscarPorTitulo(nomeLivro);
        return ResponseEntity.ok(livros);
    }


}

