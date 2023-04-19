package com.example.catalogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Produto {
    private String idProduto;
    private String nome;
    private BigDecimal preco;
}
