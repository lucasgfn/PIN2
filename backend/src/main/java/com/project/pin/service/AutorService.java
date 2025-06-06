package com.project.pin.service;

import com.project.pin.dto.Autor.AutorRequestDTO;
import com.project.pin.dto.Autor.AutorResponseDTO;
import com.project.pin.dto.Livro.LivroResponseDTO;
import com.project.pin.dto.Livro.LivroResumoDTO;
import com.project.pin.entity.Autor;
import com.project.pin.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public AutorResponseDTO cadastrarAutor(@Valid AutorRequestDTO autorRequestDTO) {
        Autor newAutor = new Autor();

        if (autorRequestDTO.nome() == null || autorRequestDTO.nome().isEmpty()) {
            throw new IllegalArgumentException("Nome do Autor é obrigatório");
        } else if (autorRequestDTO.sobre() == null || autorRequestDTO.sobre().isEmpty()) {
            throw new IllegalArgumentException("É obrigatório o autor possuir uma biografia");
        }

        newAutor.setNome(autorRequestDTO.nome());
        newAutor.setSobre(autorRequestDTO.sobre());
        newAutor.setImg(autorRequestDTO.img());


        Autor savedAutor = autorRepository.save(newAutor);
        return new AutorResponseDTO(savedAutor);
    }


    public List<AutorResponseDTO> getAll() {
        return autorRepository.findAll().stream()
                .map(AutorResponseDTO::new)
                .collect(Collectors.toList());

    }


    public ResponseEntity<AutorResponseDTO> getById(Long id) {
        return autorRepository.findById(id)
                .map(autor -> ResponseEntity.ok().body(toResponseDTO(autor))) //RETURN 200
                .orElse(ResponseEntity.notFound().build());

    }


    public ResponseEntity<Object> deletar(Long id) {
        if (autorRepository.existsById(id)) {
            autorRepository.deleteById(id);
            return ResponseEntity.noContent().build();  //RETURN 204
        }
        return ResponseEntity.notFound().build();   //RETURN 404
    }

    public ResponseEntity<AutorResponseDTO> updateAutor(Long id, AutorRequestDTO autorRequestDTO) {
        return autorRepository.findById(id)
                .map(autor -> {
                    autor.setNome(autorRequestDTO.nome());
                    autor.setImg(autorRequestDTO.img());
                    autor.setSobre(autorRequestDTO.sobre());

                    Autor updated_autor = autorRepository.save(autor);
                    return ResponseEntity.ok().body(toResponseDTO(updated_autor));
                }).orElse(ResponseEntity.notFound().build());

    }

    //-------------------------------------- AUX --------------------------------------

    private AutorResponseDTO toResponseDTO(Autor autor) {
        if (autor == null) {
            return null;
        }
        return new AutorResponseDTO(
                autor.getId(),
                autor.getNome(),
                autor.getSobre(),
                autor.getImg(),
                autor.getListLivros().stream()
                        .map(LivroResumoDTO::new)
                        .collect(Collectors.toList())

        );
    }
}