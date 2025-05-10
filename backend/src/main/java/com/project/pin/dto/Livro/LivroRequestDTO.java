package com.project.pin.dto.Livro;

import javax.validation.constraints.NotBlank;

public record LivroRequestDTO(
        Long id,
        @NotBlank(message = "Nome do livro é obrigatório")
        String nomeLivro,
        @NotBlank(message = "ISB do livro é obrigatório")
        String isbn,
        @NotBlank(message = "Sinopse do livro é obrigatório")
        String sinopse,
        @NotBlank(message = "Número de páginas do livro é obrigatório")
        int pages,
        int anoPublicado,
        @NotBlank(message = "Informe o preço unit livro")
        double precoUnit,
        String img,
        @NotBlank(message = "Informe o autor do livro (ID)")
        Long autorId

){}
