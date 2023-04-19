package com.example.pedido.controller;

import com.example.pedido.client.CatalogoClient;
import com.example.pedido.client.dto.ItemCatalogoResponse;
import com.example.pedido.dto.PedidoRequest;
import com.example.pedido.dto.PedidoResponse;
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
                    log.info("Chamando a gravação do pedido");
                    return service.salvar(pedidoRequest);
                })
                .subscribeOn(Schedulers.parallel())
                .doOnSuccess(service::validaPedido);
    }

    @GetMapping("/{id}")
    public Mono<PedidoResponse> buscarPorId(@PathVariable("id") String idPedido) {
        return Mono.defer(() -> {
                    log.info("Buscando no BD o pedido Id - {}", idPedido);
                    return service.buscarPorId(idPedido);
                })
                .subscribeOn(Schedulers.parallel());
    }


    @GetMapping
    public Flux<ItemCatalogoResponse> listaCatalogo() {
        return Flux.defer(() -> {
            log.info("Buscando catalogo na api de Produtos");
            return catalogoClient.listarCatalogo();
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping("/catalogo/{id}")
    public Mono<ItemCatalogoResponse> buscarItemCatalogo(@PathVariable("id") String idProduto) {
        return Mono.defer(() -> {
                    log.info("Buscando na api Catalogo o produto Id - {}", idProduto);
                    return catalogoClient.buscarItemCatalogo(idProduto);
                })
                .subscribeOn(Schedulers.boundedElastic());
    }
}
