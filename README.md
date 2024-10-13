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

* <b>infra</b> : Onde fica toda parte que utiliza tecnologias externas
    - <b>config</b> : Onde são definidas as configurações do spring boot e os beans que são instâncias das implementações da lógica de negócio.
    - <b>persistence</b> : Onde são definidos os repositórios e mapeamento de entidades do banco de dados.
        - <b>entity</b> : Onde são definidos os mapeamentos das entidades do banco de dados
        - <b>repository</b> : Onde são definidos os repositórios JPA da aplicação para manipuçação do banco de dados
    - <b>service</b> : Onde são definidos os adaptadores que são, também, beans de Services do spring boot. Esses adaptadores implementam portas out do módulo <b>core</b>
    - <b>mapper</b> : Onde são definidas as classes com métodos de mapeamento das entidades, classes de domínio e dtos do sistema
    - <b>util</b> : Onde ficam as classes utilitárias. Nesse caso há apenas a classe de encriptação de passwords com um algorítmo facilmente quebrável
    - <b>entrypoint</b> : Onde ficam os controllers e toda a parte utilizada apenas no controller
        - <b>controller</b> : Onde ficam as implementações dos endpoints do sistema
        - <b>dto</b> : Onde ficam as classes que mapeiam os JSONs de entrada e saída dos endpoints
        - <b>apidoc</b> : Onde ficam as implementações das anotações de documentação do sistema
    - <b>exception</b> : Onde fica a lógica de captura das exceções pelo spring boot. Classes anotadas com @ControllerAdvice.


