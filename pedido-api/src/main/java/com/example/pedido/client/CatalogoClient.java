package com.example.pedido.client;

import com.example.pedido.client.dto.ItemCatalogoResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CatalogoClient {

    private static final String CATALOGO_URI = "/v1/produtos";

    private final WebClient client;

    public CatalogoClient(WebClient.Builder clientBuilder) {
        this.client = clientBuilder
                .baseUrl("http://localhost:8080/api")
                .build();
    }

    public Flux<ItemCatalogoResponse> listarCatalogo() {

        return this.client
                .get()
                .uri(CATALOGO_URI)
                .exchangeToFlux(result -> {
                    if (result.statusCode().is2xxSuccessful()) {
                        return result.bodyToFlux(ItemCatalogoResponse.class);
                    } else {
                        return Flux.error(new RuntimeException("Erro na chamada"));
                    }
                });
    }

    public Mono<ItemCatalogoResponse> buscarItemCatalogo(String idProduto) {
        return this.client
                .get()
                .uri(CATALOGO_URI + "/" + idProduto)
                .exchangeToMono(result -> {
                    if (result.statusCode().is2xxSuccessful()) {
                        return result.bodyToMono(ItemCatalogoResponse.class);
                    } else {
                        return Mono.error(new RuntimeException("Erro na chamada"));
                    }
                });
    }

}
