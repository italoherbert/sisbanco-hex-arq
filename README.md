# Sistema de banco com Spring Boot e Arquitetura Hexagonal

Esse sistema é um simples sistema de banco com suporte a conta corrente e 
operações de: depósito, saque e transferência de dinheiro.

A arquitetura hexagonal foi utilizada para separar 
a lógica de negócios, domínio e validações do módulo de infraestrutura, 
onde são tratadas as dependências tecnológicas do projeto.

O spring security não foi utilizado numa tentativa de manter a aplicação o mais 
simples e didática possível. Logo, os endpoints são públicos e não necessitam 
de token de acesso para serem utilizados.

## Mantendo a simplicidade

Por questões de simplicidade, a senha do usuário é encriptada apenas mapeando 
seus caracteres para códigos ASCII. Inclusive, as senhas encriptadas (não as originais) são mostradas nas respostas e, geralmente, são dados sensíveis de serem mostrados assim.

## Principais endpoints do sistema

* Conta
    - Criação de conta
    - Alteração de conta
    - Remoção de conta
    - Retorno de conta pelo ID
    - Retorno de conta pelo username do usuário
    - Retorno de conta pelo email do usuário
    - Listagem de todas as contas
    - Depósito em conta corrente
    - Saque em conta corrente
    - Transferência em conta corrente

* Usuário
    - Alteração de senha
    - Listagem de usuários

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

## Estrutura de pacotes básicas dos testes da aplicação

* <b>core</b>
    - <b>domain</b> : Onde ficam os testes das validações das entidades de domínio.
    - <b>serviceimpl</b> : Onde ficam todos os <b>testes da implementação da lógica de negócio</b>.
* <b>infra</b>
    - <b>mapper</b> : Onde ficam os testes dos mappers do sistema.
    - <b>service</b> : Onde ficam os testes dos services adaptadores
    - <b>controller</b> : Onde ficam os testes dos endpoints do sistema

## Como rodar o sistema

O sistema é um microserviço só e depende apenas do jdk, versão 17, e de um banco de dados postgresql configurável no arquivo "<b>application.properties</b>" da aplicação.

### O banco de dados está com a seguinte configuração

* <b>driver</b>: org.postgresql.Driver
* <b>url</b>: jdbc:postgresql://localhost:5432/sisbanco
* <b>username</b>: postgres
* <b>password</b>: postgres

Para rodar o sistema é necessário que o banco de dados "sisbanco" já tenha sido criado!

### Empacotar utilizando o maven

Para fazer o build e empacotamento da aplicação, utilize o seguinte comando:

```
./mvnw clean package
```
### Rodar o Jar da aplicação

Para rodar, após o build bem sucedido, basta executar o jar que foi gerado e colocado na pasta "target/":

```
java -jar sisbanco-1.0-SNAPSHOT.jar
```
### Rodar o swagger

Para rodar o swagger, abra seu navegador preferido e acesse o swagger pela seguinte url:

```
http://localhost:8080/swagger-ui.html
```

### Rodar a release

Para rodar a release, basta baixar o jar disponível no github como release para este projeto e configurar o banco de dados conforme já mostrado anteriormente. Rode o jar com o seguinte comando:

```
java -jar sisbanco-1.0-SNAPSHOT.jar
```

O jar baixado corresponde ao mesmo jar gerado pelo maven no empacotamento da aplicação.

## Finalizando...

Este projeto fez bom uso da arquitetura hexagonal para separar a lógica de negócio e de domínio do restante da aplicação. Todo o módulo de core, não tem dependência direta de outras tecnologias mas, sim, é utilizado pelo módulo de infraestrutura da aplicação.

Espero que gostem do projeto e, até o próximo...
