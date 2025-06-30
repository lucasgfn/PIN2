package com.project.pin.service;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.dto.Comprador.CompradorResponseDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.exceptions.UserFoundException;
import com.project.pin.mapper.CompradorMapper;
import com.project.pin.repository.CompradorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class CompradorService {
    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private CompradorMapper compradorMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Comprador cadastrarComprador(Comprador comprador) {
        this.compradorRepository.findByCpfOrUsername(comprador.getCpf(), comprador.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

        comprador.setPassword(passwordEncoder.encode(comprador.getPassword()));
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


    @Transactional
    public Comprador updateComprador(Long id, CompradorRequestDTO dto) {
        System.out.println("🚨 DTO recebido: " + dto);

        if (dto == null) {
            throw new IllegalArgumentException("Informe algum campo para atualizar dados");
        }

        Comprador comprador = compradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado"));
        comprador.setNome(dto.nome()); // manualmente para ver se altera

        System.out.println("Antes: " + comprador);
        compradorMapper.updateFromDto(dto, comprador);
        System.out.println("Depois: " + comprador);

        ///compradorMapper.updateFromDto(dto, comprador);

        return compradorRepository.save(comprador);
    }

    public Comprador autenticar(String username, String senha) {
        Optional<Comprador> optionalComprador = compradorRepository.findByUsername(username);
        if (optionalComprador.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Comprador comprador = optionalComprador.get();

        // Verifica se a senha crua bate com a senha codificada no banco
        if (!passwordEncoder.matches(senha, comprador.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }

        return comprador;
    }

    public Comprador buscarPorUsername(String username) {
        return compradorRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com username: " + username));
    }


    public CompradorResponseDTO getInfosComprador(Long id) {
        Comprador comprador = compradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comprador não encontrado"));

        return new CompradorResponseDTO(
                comprador.getId(),
                comprador.getNome(),
                comprador.getEmail(),
                comprador.getCpf(),
                comprador.getUsername(),
                comprador.getRua(),
                comprador.getTelefone(),
                comprador.getBairro(),
                comprador.getCidade(),
                comprador.getCep(),
                comprador.getEstado(),
                comprador.getImg(),
                comprador.getNivel(),
                comprador.isRecebeuDesconto());
    }

}

