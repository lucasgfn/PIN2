package com.project.pin.service;

import com.project.pin.entity.Comprador;
import com.project.pin.exceptions.UserFoundException;
import com.project.pin.repository.CompradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
public class CompradorService {
    @Autowired
    private CompradorRepository compradorRepository;

    public Comprador cadastrarComprador(Comprador comprador) {
        this.compradorRepository.findByCpfOrUsername(comprador.getCpf(), comprador.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

            //tratar imagem antes de mandar pro banco
            return this.compradorRepository.save(comprador);

    }

    @Transactional
    public void deletarComprador(Long id) {
        Optional<Comprador> compradorExistente = this.compradorRepository.findById(id);

        if(compradorExistente.isEmpty()) {
            throw new IllegalArgumentException("Usuário não existe");
        }

        this.compradorRepository.delete(compradorExistente.get());
    }

}
