package com.example.catalogo.service;

import com.example.catalogo.model.ItemEmEstoque;
import com.example.catalogo.model.Produto;
import com.example.catalogo.repository.InMemoryCatalogo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final InMemoryCatalogo repository;

    public Flux<ItemEmEstoque> getAll() {
        log.info("Iniciando busca de todos produtos no banco de dados");
        return repository.findAll()
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(it -> log.info("Resgatando todos produtos do banco de dados - {}", it));
    }

    public Mono<ItemEmEstoque> getByIdProduto(String idProduto) {
        log.info("Iniciando busca do produtos com id - {}", idProduto);
        return repository.findByIdProduto(idProduto)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(it -> log.info("Resgatando produto com id - {}", it));
    }


}
