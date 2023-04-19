package com.example.pedido.client.dto;

import java.math.BigDecimal;

public record ItemCatalogoResponse(String id, ProdutoCatalogo produto, Long quantidade) {
}
