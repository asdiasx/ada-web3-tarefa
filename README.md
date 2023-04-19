# ada-web3-tarefa
Tarefa do modulo Web III 

## Enunciado

### Exercicio: API's assíncronas com webflux

Crie duas API's e um API gateway, uma de Catalogo/Produtos e uma de Pedido.

API de catalogo:

deve expor chamadas REST para listar todos os Produtos que é composto por id, nome, preco e quantidade no estoque

API de pedidos:

Deve export chamadas REST para efetuar um pedido e consultar um pedido o Pedido deve ser composto por id, uma lista de itens, data hora, status e total do pedido. Um item deve ter id do produto e a quantidade.

A api de pedidos deve confirmar se cada produto existe e se tem estoque suficiente antes de confirmar o pedido

os status do pedido devem ser REALIZADO, CONFIRMADO, ERRO_NO_PEDIDO e ENVIADO PARA ENTREGA
