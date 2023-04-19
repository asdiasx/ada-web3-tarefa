package com.example.pedido.dto;

import com.example.pedido.model.Item;
import com.example.pedido.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        String idPedido,
        List<ItemDTO> itens,
        Pedido.Status status
) {
}
