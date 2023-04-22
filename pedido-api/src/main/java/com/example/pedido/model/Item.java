package com.example.pedido.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String idProduto;
    private Long quantidade;
}
