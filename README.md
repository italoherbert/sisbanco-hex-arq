# sistema de banco com Spring Boot e Arquitetura Hexagonal

Este sistema foi construído utilizando arquitetura hexagonal para separar 
a lógica de negócios, domínio e validações da camada de infraestrutura, 
onde são tratadas as dependências tecnológicas do projeto.

## Estrutura de pacotes básica da aplicação

Todas as subpastas abaixo estão na pasta <b>italo/sisbanco/</b>.

* <b>core</b> : Núcleo do sistema (independente de tecnologias externas)
    - <b>domain</b> : Responsável pela definição das entidades de domínio utilizadas na  implementação da lógica de negócios.
    - <b>validation</b> : Onde fica a lógica das validações.
    - <b>exception</b> : Onde estão as classes de exceção de Domínio e Negócio com as mensagens de erro.
    - <b>ports</b> : Onde estão as interfaces de entrada e saída do módulo core.
        - <b>in</b> : Onde são definidas as interfaces de serviços implementados no pacote de <b>serviceimpl</b>.
        - <b>out</b> : Onde são definidas as interfaces implementadas fora do core que dependem de tecnologias externas.
    - <b>serviceimpl</b> : Onde está toda a lógica de negócio independente das tecnologias externas utilizadas no projeto.
 
* <b>error</b> : Onde é definida a classe de exceção principal herdada pelas exceptions do módulo <b>core</b>.

* <b>infra</b>
    - <b>config</b> :

