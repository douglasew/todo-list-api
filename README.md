# API TodoList em Spring Boot

Esta é uma API TodoList desenvolvida em Spring Boot. Ela permite criar, listar, atualizar e excluir tarefas.

## Requisitos

- Java Development Kit (JDK) 17
- Docker e Docker Compose (para executar a aplicação em contêineres)

## Configuração

1. Clone este repositório:

```sh
git clone https://github.com/douglasew/todo-list-api
```

2. Acesse o diretório do projeto:

```sh
cd todo-list-api
```

3. Execute o Docker Compose para subir os contêineres da aplicação e do banco de dados:

```sh
cd docker
docker-compose up
```

## Executando a aplicação

1. Volte ao diretório principal do projeto:

```sh
cd ..
```

2. Compile a aplicação:

```sh
./mvnw clean package
```

3. Inicie a aplicação:

```sh
java -jar target/todo-list-api.jar
```

## Documentação da API

Você pode acessar a documentação da API em http://localhost:8080/swagger-ui.html.
