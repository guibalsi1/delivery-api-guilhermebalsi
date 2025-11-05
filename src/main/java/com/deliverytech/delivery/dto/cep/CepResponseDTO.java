package com.deliverytech.delivery.dto.cep;

public record CepResponseDTO(
        String cep,
        String logradouro,
        String bairro,
        String latitude,
        String longitude,
        Cidade cidade,
        Estado estado
) {

    public record Cidade(
            String ibge,
            String nome,
            Integer ddd
    ) {}

    public record Estado(
            String sigla
    ) {}
}
