package com.example.pedido.repository;

import com.example.pedido.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class PedidoRepository {
    private static final List<Pedido> PEDIDOS_DB =
            new CopyOnWriteArrayList<>();

    public void salvar(Pedido pedido) {
        log.info("Salvando Pedido no DB");
        PEDIDOS_DB.add(pedido);
    }

    public Pedido buscarPorId(String idPedido) {
        log.info("Lendo pedido do DB - {}", idPedido);

        return PEDIDOS_DB.stream()
                .filter(pedido -> pedido.getIdPedido().equals(idPedido))
                .findFirst()
                .orElseThrow();
    }

    public void atualizar(Pedido pedido) {
        log.info("Atualizando pedido no DB");
        var pedidoAtual = PEDIDOS_DB.stream()
                .filter(item -> item.getIdPedido().equals(pedido.getIdPedido()))
                .findFirst()
                .orElse(null);
        PEDIDOS_DB.remove(pedidoAtual);
        PEDIDOS_DB.add(pedido);
    }
}
