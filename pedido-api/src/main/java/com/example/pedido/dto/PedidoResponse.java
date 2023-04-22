package com.example.pedido.dto;

import com.example.pedido.model.Pedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(
        String idPedido,
        List<ItemDTO> itens,
        BigDecimal total,
        Pedido.Status status
) {
}
