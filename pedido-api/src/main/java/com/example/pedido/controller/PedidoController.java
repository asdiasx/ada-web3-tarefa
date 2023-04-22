package com.example.pedido.controller;

import com.example.pedido.client.CatalogoClient;
import com.example.pedido.client.dto.ItemCatalogoResponse;
import com.example.pedido.dto.PedidoRequest;
import com.example.pedido.dto.PedidoResponse;
import com.example.pedido.service.AtualizaPedidoRunnable;
import com.example.pedido.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("api/v1/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final CatalogoClient catalogoClient;

    @PostMapping
    public Mono<PedidoResponse> salvar(@RequestBody PedidoRequest pedidoRequest) {
        return Mono.defer(() -> {
                    log.info("Iniciando a gravação do pedido");
                    return service.salvar(pedidoRequest);
                })
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping("/{id}")
    public Mono<PedidoResponse> buscarPorId(@PathVariable("id") String idPedido) {
        return Mono.defer(() -> {
                    log.info("Iniciando busca no BD o pedido Id - {}", idPedido);
                    return service.buscarPorId(idPedido);
                })
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping
    public Flux<ItemCatalogoResponse> testeListaCatalogo() {
        return Flux.defer(() -> {
            log.info("Iniciando busca de Catalogo pela api de Pedidos");
            return catalogoClient.listarCatalogo();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/catalogo/{id}")
    public Mono<ItemCatalogoResponse> testeBuscarItemCatalogo(@PathVariable("id") String idProduto) {
        return Mono.defer(() -> {
                    log.info("Iniciando busca de produto no Catalogo pela api de Pedidos, produto id - {}", idProduto);
                    return catalogoClient.buscarItemCatalogo(idProduto);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
