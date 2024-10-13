# sistema de banco com Spring Boot e Arquitetura Hexagonal

Este sistema foi construído utilizando arquitetura hexagonal para separar 
a lógica de negócios, domínio e validações da camada de infraestrutura, 
onde são tratadas as dependências tecnológicas do projeto.

## Estrutura de pacotes básica da aplicação

* italo/sisbanco/core
    - <b>domain</b> : Responsável pela definição das entidades de domínio utilizadas na  implementação da lógica de negócios
    - <b>validation</b> : Onde fica a lógica das validações
    - <b>exception</b> : Onde estão as classes de exceção de Domínio e Negócio com as mensagens de erro
    - <b>

