package com.example.pedido.service;


import com.example.pedido.client.CatalogoClient;
import com.example.pedido.dto.ItemDTO;
import com.example.pedido.dto.PedidoRequest;
import com.example.pedido.dto.PedidoResponse;
import com.example.pedido.model.Item;
import com.example.pedido.model.Pedido;
import com.example.pedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repository;
    private final CatalogoClient catalogoClient;

    public Mono<PedidoResponse> salvar(PedidoRequest pedidoRequest) {
        var pedido = Pedido.builder()
                .idPedido(UUID.randomUUID().toString())
                .itens(pedidoRequest.itens().stream()
                        .map(itemDTO -> new Item(itemDTO.idProduto(), itemDTO.quantidade()))
                        .toList())
                .dataHora(LocalDateTime.now())
                .status(Pedido.Status.REALIZADO)
                .total(BigDecimal.ZERO)
                .build();
        repository.salvar(pedido);
        return Mono.just(new PedidoResponse(pedido.getIdPedido(),
                pedidoRequest.itens(),
                pedido.getStatus()
        ));
    }

    public Mono<PedidoResponse> buscarPorId(String idPedido) {
        return Mono.defer(() -> Mono.justOrEmpty(repository.buscarPorId(idPedido)))
                .subscribeOn(Schedulers.boundedElastic())
                .map(entidade ->
                        new PedidoResponse(
                                entidade.getIdPedido(),
                                entidade.getItens().stream()
                                        .map(itemEntidade -> new ItemDTO(itemEntidade.getIdProduto(), itemEntidade.getQuantidade()))
                                        .toList(),
                                entidade.getStatus()));
    }

    public Mono<Void> validaPedido(PedidoResponse pedidoResponse) {
//        log.info("Validando pedido - {}", pedidoResponse);
//        var itensPedido = pedidoResponse.itens();
//        return itensPedido.stream()
//                .map(
//                        item -> {
//                            final var idProduto = item.idProduto();
//                            var itemCatalogo = catalogoClient.buscarItemCatalogo(idProduto).subscribe();
//                            log.info("Verificando se tem saldo suficiente do produto - {}", itemCatalogo);
//                            if (itemCatalogo.quantidade() < item.quantidade()) {
//                                log.error("Quantidade insuficiente do produto - {}", itemCatalogo);
//                                return new RuntimeException("Quantidade insuficiente do produto");
//                            } else {
//                                return itemCatalogo;
//                            }
//                                    .map(itemCatalogo -> {
//                                        log.info("Calculando valor to item - {}", itemCatalogo);
//
//                                        return (itemCatalogo.produto().preco()
//                                                .multiply(BigDecimal.valueOf(item.quantidade())));
//                                    }).subscribe();
//                        }
//                )
//                .sum();
        return null;
    }
}
