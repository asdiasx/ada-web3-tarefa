package com.example.pedido.service;

import com.example.pedido.client.CatalogoClient;
import com.example.pedido.model.Item;
import com.example.pedido.model.Pedido;
import com.example.pedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
public class AtualizaPedidoRunnable implements Runnable {

    private final PedidoRepository repository;
    private final CatalogoClient catalogoClient;
    private final Pedido pedido;

    @Override
    public void run() {
        System.out.println("Início do runnable para atualizar pedido " + Thread.currentThread() + " " + pedido);
        this.atualizaPedido(pedido);
    }

    private void atualizaPedido(Pedido pedido) {
        AtomicBoolean error = new AtomicBoolean(false);
        List<Item> itensPedido = pedido.getItens();
        AtomicReference<BigDecimal> total = new AtomicReference<>(BigDecimal.ZERO);

        Flux.fromIterable(itensPedido)
                .flatMap(
                        item -> {
                            log.info("Verificando se produto existe");
                            return catalogoClient.buscarItemCatalogo(item.getIdProduto())
                                    .switchIfEmpty(Mono.fromRunnable(() -> {
                                        log.error("ERROR: Produto não existe - {}", item.getIdProduto());
                                        error.set(true);
                                    }))
                                    .doOnNext(itemCatalogo -> {
                                                log.info("Verificando se quantidade suficiente");
                                                if (itemCatalogo.quantidade() < item.getQuantidade()) {
                                                    log.error("ERROR: Quantidade insuficiente para o produto - {}", item.getIdProduto());
                                                    error.set(true);
                                                } else {
                                                    total.updateAndGet(valor -> valor
                                                            .add(itemCatalogo.produto().preco()
                                                                    .multiply(BigDecimal.valueOf(item.getQuantidade()))));
                                                }
                                            }
                                    );
                        }
                )
                .collectList()
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(list -> {
                    if (error.get()) {
                        pedido.setStatus(Pedido.Status.ERRO_NO_PEDIDO);
                        repository.atualizar(pedido);
                    } else {
                        pedido.setStatus(Pedido.Status.CONFIRMADO);
                        pedido.setTotal(total.get());
                        repository.atualizar(pedido);
                    }
                })
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}
