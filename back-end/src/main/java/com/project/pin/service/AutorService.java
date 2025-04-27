package com.project.pin.service;

import com.project.pin.dto.AutorRequestDTO;
import com.project.pin.dto.AutorResponseDTO;
import com.project.pin.entity.Autor;
import com.project.pin.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;
    private static final String DEFAULT_IMAGE_URL = "back-end/src/main/resources/static/images/default-img-profile.jpg";

    public AutorResponseDTO cadastrarAutor(@Valid AutorRequestDTO autorRequestDTO) {
        Autor newAutor = new Autor();

        if (autorRequestDTO.nome() == null || autorRequestDTO.nome().isEmpty()) {
            throw new IllegalArgumentException("Nome do Autor é obrigatório");
        } else if (autorRequestDTO.sobre() == null || autorRequestDTO.sobre().isEmpty()) {
            throw new IllegalArgumentException("É obrigatório o autor possuir uma biografia");
        }

        newAutor.setNome(autorRequestDTO.nome());
        newAutor.setSobre(autorRequestDTO.sobre());
        if (autorRequestDTO.img() != null && autorRequestDTO.img().isEmpty()){  //GARANTE QUE A IMAGEM DEFAULT NAO SEJA SALVA NO BD
            newAutor.setImg(autorRequestDTO.img());
        }else{
            newAutor.setImg(null);
        }

        Autor savedAutor = autorRepository.save(newAutor);
        return new AutorResponseDTO(savedAutor.getId(), savedAutor.getNome(), savedAutor.getSobre(), getImageUrl(savedAutor), savedAutor.getListLivros());
    }

    //DESSA FORMA PODEMOS RECUPERAR A IMAGEM NO FRONT
    private String getImageUrl(Autor autor) {
        return (autor.getImg() != null && !autor.getImg().isEmpty()) ? autor.getImg() : DEFAULT_IMAGE_URL;
    }

}
