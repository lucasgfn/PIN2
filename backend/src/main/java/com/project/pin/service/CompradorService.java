package com.project.pin.service;

/*
@Service
public class CompradorService {
    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private CompradorMapper compradorMapper;

    public Comprador cadastrarComprador(Comprador comprador) {
        this.compradorRepository.findByCpfOrUsername(comprador.getCpf(), comprador.getUsername())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });

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
                .orElseThrow(UserFoundException::new);

        compradorMapper.updateFromDto(dto, comprador);

        return compradorRepository.save(comprador);
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
    */
