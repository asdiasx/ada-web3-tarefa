package com.example.pedido.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemDTO(
        @JsonProperty("produto")
        String idProduto,
        Long quantidade
) {
}
