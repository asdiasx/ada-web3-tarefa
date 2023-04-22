package com.example.pedido.dto;

import java.util.List;

public record PedidoRequest(List<ItemDTO> itens) {}
