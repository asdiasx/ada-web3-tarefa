package com.example.pedido.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
//@Document(value = "produtos")
public class Item {
    private String idProduto;
    private Long quantidade;
}
