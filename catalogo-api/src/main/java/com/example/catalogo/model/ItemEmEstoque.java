package com.example.catalogo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemEmEstoque {
    private String id;
    private Produto produto;
    private Long quantidade;
}
