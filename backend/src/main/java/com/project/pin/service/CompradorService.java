package com.project.pin.service;

import com.project.pin.dto.Comprador.CompradorRequestDTO;
import com.project.pin.dto.Comprador.CompradorResponseDTO;
import com.project.pin.entity.Comprador;
import com.project.pin.exceptions.UserFoundException;
import com.project.pin.repository.CompradorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompradorService {
    @Autowired
    private CompradorRepository compradorRepository;

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
        if (dto == null) {
            throw new IllegalArgumentException("Informe algum campo para atualizar dados");
        }

        Comprador comprador = compradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comprador não encontrado"));

        // Atualiza apenas os campos não-nulos
        if (dto.nome() != null) comprador.setNome(dto.nome());
        if (dto.email() != null) comprador.setEmail(dto.email());
        if (dto.cpf() != null) comprador.setCpf(dto.cpf());
        if (dto.username() != null) comprador.setUsername(dto.username());
        if (dto.rua() != null) comprador.setRua(dto.rua());
        if (dto.telefone() != null) comprador.setTelefone(dto.telefone());
        if (dto.bairro() != null) comprador.setBairro(dto.bairro());
        if (dto.cidade() != null) comprador.setCidade(dto.cidade());
        if (dto.cep() != null) comprador.setCep(dto.cep());
        if (dto.estado() != null) comprador.setEstado(dto.estado());

        // Atualiza imagem, mesmo que seja null (para permitir remoção)
        comprador.setImg(dto.img());

        // Atualiza senha se não for null (com codificação)
        if (dto.senha() != null) {
            comprador.setPassword(passwordEncoder.encode(dto.senha()));
        }
        
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

