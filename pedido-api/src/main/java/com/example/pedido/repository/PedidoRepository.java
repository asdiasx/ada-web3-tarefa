package com.example.pedido.repository;


import com.example.pedido.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class PedidoRepository {
    private static final List<Pedido> PEDIDOS_DB =
            new CopyOnWriteArrayList<>();

    public void salvar(Pedido pedido) {
        PEDIDOS_DB.add(pedido);
    }

    public Pedido buscarPorId(String idPedido){

        return PEDIDOS_DB.stream()
                .filter( pedido -> pedido.getIdPedido().equals(idPedido))
                .findFirst()
                .orElseThrow();
    }

    public List<Pedido> buscarTodos() {
        return new ArrayList<>(PEDIDOS_DB);
    }
}
