package com.example.pedido.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@With
public class Pedido {
    private String idPedido;
    private List<Item> itens;
    private LocalDateTime dataHora;
    private Status status;
    private BigDecimal total;

    public enum Status {
        REALIZADO,
        CONFIRMADO,
        ERRO_NO_PEDIDO,
        ENVIADO_PARA_ENTREGA
    }
}
