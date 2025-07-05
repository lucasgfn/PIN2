package com.project.pin.service;


import com.project.pin.dto.Livro.LivroRequestDTO;
import com.project.pin.dto.Livro.LivroResponseDTO;
import com.project.pin.entity.Autor;
import com.project.pin.entity.Livro;
import com.project.pin.repository.AutorRepository;
import com.project.pin.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {
    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;

    public LivroResponseDTO cadastrarLivro(@Valid LivroRequestDTO livroRequestDTO){
        Livro newLivro = new Livro();
        if (livroRequestDTO.autorId() == null) {
            throw new IllegalArgumentException("ID do Autor é obrigatório para cadastrar Livro");
        }

        Autor autor = buscarAutor(livroRequestDTO.autorId());

        newLivro.setNomeLivro(livroRequestDTO.nomeLivro());
        newLivro.setIbsn(livroRequestDTO.isbn());
        newLivro.setSinopse(livroRequestDTO.sinopse());
        newLivro.setPages(livroRequestDTO.pages());
        newLivro.setAnoPublicado(livroRequestDTO.anoPublicado());
        newLivro.setPrecoUnit(livroRequestDTO.precoUnit());
        newLivro.setImg(livroRequestDTO.img());

        newLivro.setAutor(autor);

        livroRepository.save(newLivro);
        return new LivroResponseDTO(newLivro);
    }


    public List<LivroResponseDTO> getAll() {
        List<Livro> livros = livroRepository.findAll();

        if (livros.isEmpty()) {
            return List.of();
        }

        return livros.stream()
                .map(LivroResponseDTO::new)
                .collect(Collectors.toList());
    }


    public ResponseEntity<LivroResponseDTO> getById(Long id) {
        return livroRepository.findById(id)
                .map(livro -> ResponseEntity.ok().body(toResponseDTO(livro))) //RETURN 200
                .orElse(ResponseEntity.notFound().build());

    }


    public ResponseEntity<Object> deletar(Long id) {
        if(livroRepository.existsById(id)){
            livroRepository.deleteById(id);
            return ResponseEntity.noContent().build();  //RETURN 204
        }
        return ResponseEntity.notFound().build();   //RETURN 404
    }

    public ResponseEntity<LivroResponseDTO> updateLivro(Long id, LivroRequestDTO livroRequestDTO) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livro.setNomeLivro(livroRequestDTO.nomeLivro());
                    livro.setIbsn(livroRequestDTO.isbn());
                    livro.setSinopse(livroRequestDTO.sinopse());
                    livro.setPages(livroRequestDTO.pages());
                    livro.setAnoPublicado(livroRequestDTO.anoPublicado());
                    livro.setPrecoUnit(livroRequestDTO.precoUnit());
                    livro.setImg(livroRequestDTO.img());

                    if (livroRequestDTO.autorId() != null) {
                        Autor autor = buscarAutor(livroRequestDTO.autorId());
                        livro.setAutor(autor);
                    }

                    Livro updated_livro = livroRepository.save(livro);
                    return ResponseEntity.ok().body(toResponseDTO(updated_livro));
                }).orElse(ResponseEntity.notFound().build());

    }

    @Transactional
    public List<LivroResponseDTO> buscarPorTitulo(String nomeLivro) {
        List<Livro> livros = livroRepository.findByNomeLivroContainingIgnoreCase(nomeLivro);
        return livros.stream().map(LivroResponseDTO::new).toList();
    }




    // ---------------------- AUX ----------------------

    private Autor buscarAutor(Long idDTO) {
        return autorRepository.findById(idDTO)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado ou não cadastrado (Referente a ID: " + idDTO));
    }

    private LivroResponseDTO toResponseDTO(Livro livro) {
        if(livro == null){
            return null;
        }

        return new LivroResponseDTO(
                livro.getId(),
                livro.getIbsn(),
                livro.getNomeLivro(),
                livro.getSinopse(),
                livro.getPages(),
                livro.getAnoPublicado(),
                livro.getPrecoUnit(),
                livro.getImg(),
                livro.getAutor().getId(),
                livro.getAutor().getNome()
                );
    }

}
