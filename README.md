# ada-web3-tarefa
Tarefa do modulo Web III

## Detalhes da solução

A funcionalidade esperada é de que a gravação de um novo pedido é feita imediatamente sem valor e com status de REALIZADO.

Após salvar o pedido e retornar resposta para a solicitação, o sistema cria uma nova thread para realizar a atualização do pedido, que consiste em:

- Pesquisar cada item na api de catálogo.
  - Caso não exista o produto ou caso a quantidade não seja suficiente, atualiza o pedido com o status ERRO_NO_PEDIDO.
  - Caso contrário o valor total é atualizado e ao final dos itens, o pedido é atualizado com o valor total e status CONFIRMADO

Foi incluido um arquivo com testes de rotas exportado do Http client Insomnia.

## Enunciado

### Exercicio: API's assíncronas com webflux

Crie duas API's e um API gateway, uma de Catalogo/Produtos e uma de Pedido.

API de catalogo:

deve expor chamadas REST para listar todos os Produtos que é composto por id, nome, preco e quantidade no estoque

API de pedidos:

Deve export chamadas REST para efetuar um pedido e consultar um pedido o Pedido deve ser composto por id, uma lista de itens, data hora, status e total do pedido. Um item deve ter id do produto e a quantidade.

A api de pedidos deve confirmar se cada produto existe e se tem estoque suficiente antes de confirmar o pedido

os status do pedido devem ser REALIZADO, CONFIRMADO, ERRO_NO_PEDIDO e ENVIADO PARA ENTREGA
