# Reserva de Restaurante

## Descrição do Projeto
Este projeto é um Sistema de Reservas para Restaurantes, desenvolvido para gerenciar a criação, consulta e gerenciamento de mesas e reservas. A aplicação permite que usuários autenticados reservem mesas, enquanto administradores têm controle total sobre a criação, edição e exclusão de mesas.

O sistema foi arquitetado com uma separação clara de responsabilidades:

Mesa Service: Gerencia todas as operações e lógicas relacionadas às mesas, como a validação de disponibilidade e capacidade.

Reserva Service: Orquestra o processo de reserva, garantindo que a transação seja segura e que os dados (reserva e status da mesa) sejam consistentes.

A segurança e a integridade dos dados são prioritárias, utilizando autenticação via token para garantir que cada usuário só possa gerenciar suas próprias reservas e que as ações críticas de atualização de status sejam realizadas de forma transacional e segura.

