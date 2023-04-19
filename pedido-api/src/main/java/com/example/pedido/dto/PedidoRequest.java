package com.example.pedido.dto;

import com.example.pedido.model.Pedido;
import com.example.pedido.model.Item;

import java.time.Instant;
import java.util.List;

public record PedidoRequest(List<ItemDTO> itens) {}
