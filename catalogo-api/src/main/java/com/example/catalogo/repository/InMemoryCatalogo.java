package com.example.catalogo.repository;

import com.example.catalogo.model.ItemEmEstoque;
import com.example.catalogo.model.Produto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class InMemoryCatalogo {

    private static final List<ItemEmEstoque> ESTOQUE_ITEMS;

    static {
        var produto1 = new Produto("123", "Cadeira", new BigDecimal("2300.0"));
        var produto2 = new Produto("456", "Mouse Gamer", new BigDecimal("950.0"));

        ESTOQUE_ITEMS = new ArrayList<>();
        ESTOQUE_ITEMS.add(new ItemEmEstoque(uidGen(), produto1, 10L));
        ESTOQUE_ITEMS.add(new ItemEmEstoque(uidGen(), produto2, 20L));
    }

    public static String uidGen() {
        return UUID.randomUUID().toString();
    }

    @SneakyThrows
    public Flux<ItemEmEstoque> findAll() {
        return Flux.defer(() -> {
            log.info("Buscando todos produtos do DB");
            return Flux.fromIterable(ESTOQUE_ITEMS);
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @SneakyThrows
    public Mono<ItemEmEstoque> findByIdProduto(String idProduto) {
        return Mono.defer(() -> {
            log.info("Buscando produto do DB");
            return Mono.justOrEmpty(ESTOQUE_ITEMS.stream().filter(item -> item.getProduto().getIdProduto().equals(idProduto)).findFirst());
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
