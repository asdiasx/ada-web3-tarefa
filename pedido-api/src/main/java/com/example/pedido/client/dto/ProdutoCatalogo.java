package com.example.pedido.client.dto;

import java.math.BigDecimal;

public record ProdutoCatalogo(String idProduto, String nome, BigDecimal preco) {
}
