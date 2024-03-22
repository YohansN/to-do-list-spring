# Desafio Técnico Backend - [NUVEN]
O projeto consiste na implementação de uma API REST com funcionalidades de **To-Do list** em Java Spring Boot.

## Funcionalidades
- Cadastro de nova tarefa.
- Visualização de tarefas cadastradas.
- Atualização de tarefa.
- Remoção de tarefa.

## Como rodar o projeto e seus testes
**Pré-requisito:** Ter o [Docker](https://www.docker.com) instalado.
1. Clone este repositório.
   ```
   git clone https://github.com/YohansN/to-do-list-spring.git
   ```

2. Vá até o diretório base do projeto.
   ```
   cd To-Do-List
   ```

3. Execute o projeto:
   ```
   docker-compose up --build
   ```

4. Agora a aplicação está disponível em:
- Localmente: http://localhost:8080/todo/task
- Documentação Swagger: http://localhost:8080/swagger-ui/index.html
- **OBS:** A aplicação também está disponível de forma remota em: https://nuven-todo.onrender.com/todo/task

5. Para parar a execução e excluir os containers use o comando:
    ```
    docker-compose down
    ```
**OU**
<details>
	<summary><strong>Caso não tenha o Docker instalado</strong></summary>
	<p>Nesse caso é necessário a instalação das dependências e suas configurações:</p>
	<ul>
		<li>Java - SDK 21</li>
		<li>Maven</li>
		<li>PostgreSQL - Necessário modificações no arquivo application.properties</li>
	</ul>

3b. Faça o build do projeto:
```mvn clean package```

4b. Execute o projeto:
```java -jar target/To-Do-List-0.0.1-SNAPSHOT.jar```
</details>  

5. Para rodar apenas os **testes** (dentro da pasta raiz do projeto):
    ``` 
    mvn test
    ```

## End-Points/Rotas
POST ```/todo/task``` - Retorna cadastra uma nova tarefa.

GET ```/todo/task``` - Retorna a lista de tarefas cadastradas.

PUT ```/todo/task```  - Atualiza uma tarefa existente.

DELETE ```todo/task/{id}``` - Remove uma tarefa.

## Tecnologias
- Java
- Spring Boot
- Spring Web
- Spring Data JPA - Repositório simplificado para comunicação do a DB.
- PostgreSQL - Banco de dados sequencial da aplicação.
- H2 DB - Banco de dados utilizado para testes.
- Flyway - Criação de migrations.
- Lombok - Anotações para redução de código boilerplate e aumento da produtividade.
- SwaggerUI - Documentação do projeto.
- JUnit - Testes unitários.
- Docker - Conteinerização do projeto.


---
Esse projeto foi feito para o desafio técnico, vaga BackEnd Java do [Núcleo de visão computacional e engenharia](https://labnuven.com.br/)