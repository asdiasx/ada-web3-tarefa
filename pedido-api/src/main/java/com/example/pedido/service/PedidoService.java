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
        log.info("Gerando instancia do pedido");
        var pedido = Pedido.builder()
                .idPedido(UUID.randomUUID().toString())
                .itens(pedidoRequest.itens().stream()
                        .map(itemDTO -> new Item(itemDTO.idProduto(), itemDTO.quantidade()))
                        .toList())
                .dataHora(LocalDateTime.now())
                .status(Pedido.Status.REALIZADO)
                .total(BigDecimal.ZERO)
                .build();
        return Mono.defer(() -> {
                    log.info("Chamada no DB para salvar o pedido criado");
                    repository.salvar(pedido);
                    new Thread(new AtualizaPedidoRunnable(repository, catalogoClient, pedido)).start();
                    return Mono.just(new PedidoResponse(
                            pedido.getIdPedido(),
                            pedidoRequest.itens(),
                            null,
                            pedido.getStatus()));
                })
                .subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<PedidoResponse> buscarPorId(String idPedido) {

        return Mono.defer(() -> {
                            log.info("Chamada no DB para buscar o pedido - {}", idPedido);
                            return Mono.justOrEmpty(repository.buscarPorId(idPedido));
                        }
                )
                .subscribeOn(Schedulers.boundedElastic())
                .map(entidade ->
                        new PedidoResponse(
                                entidade.getIdPedido(),
                                entidade.getItens().stream()
                                        .map(itemEntidade -> new ItemDTO(itemEntidade.getIdProduto(), itemEntidade.getQuantidade()))
                                        .toList(),
                                entidade.getTotal(),
                                entidade.getStatus()));
    }
}
