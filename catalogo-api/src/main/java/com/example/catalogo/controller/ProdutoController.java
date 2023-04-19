package com.example.catalogo.controller;

import com.example.catalogo.model.ItemEmEstoque;
import com.example.catalogo.model.Produto;
import com.example.catalogo.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@RestController
@RequestMapping("api/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @GetMapping
    public Flux<ItemEmEstoque> getAll() {
        return Flux.defer(service::getAll)
                .subscribeOn(Schedulers.parallel());
    }

    @GetMapping("/{id}")
    public Mono<ItemEmEstoque> getByIdProduto(@PathVariable("id") String idProduto) {
        return Mono.defer(() -> service.getByIdProduto(idProduto))
                .subscribeOn(Schedulers.parallel());
    }
}
