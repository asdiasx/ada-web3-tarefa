# Detalhes da solução

A funcionalidade esperada é de que a gravação de um novo pedido é feita imediatamente sem valor e com status de REALIZADO.

Após salvar o pedido, o sistema chama uma nova thread para realizar a atualização do pedido, que consiste em:

- Pesiquisar cada item na api de catálogo.
  - Caso não exista o produto ou caso a quantidade não seja suficiente, atualiza o pedido com o status ERRO_NO_PEDIDO.
  - Caso contrário o valor total é atualizado e ao final dos itens, o pedido é atualizado com o valor total e status CONFIRMADO

Foi incluido um arquivo com testes de rotas exportado do Http client Insomnia.
