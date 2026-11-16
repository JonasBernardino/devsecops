# DevSecOps Lab — API de alunos

API REST didática para cadastro e consulta de alunos, desenvolvida para atividades práticas de DevOps na UNIESP.

## Tecnologias

- Java 21 e Spring Boot 3
- Maven e Maven Wrapper
- Spring Web, Spring JDBC e Spring Boot Actuator
- H2 em memória
- JUnit 5 e MockMvc
- Docker e GitHub Actions

## Requisitos

- JDK 21
- Docker (opcional, para executar em contêiner)

O projeto não depende de serviços externos. O banco H2 é iniciado e populado automaticamente com dados fictícios em cada execução.

## Executar localmente

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Endpoints

| Método | Caminho | Descrição |
| --- | --- | --- |
| `GET` | `/api/alunos` | Lista os alunos |
| `GET` | `/api/alunos/{id}` | Consulta um aluno pelo ID |
| `GET` | `/api/alunos/buscar?nome=Pedro` | Busca alunos pelo nome |
| `GET` | `/api/alunos/exportar.csv` | Exporta os alunos em CSV |
| `POST` | `/api/alunos` | Cadastra um aluno |
| `GET` | `/actuator/health` | Consulta a saúde da aplicação |

Exemplo de cadastro:

```bash
curl -X POST http://localhost:8080/api/alunos \
  -H 'Content-Type: application/json' \
  -d '{"nome":"Rafaela Lima","email":"rafaela@example.test","curso":"DevOps"}'
```

## Testes e build

```bash
./mvnw test
./mvnw verify
```

No Windows, substitua `./mvnw` por `.\mvnw.cmd`.

## Docker

Gere o artefato e construa a imagem:

```bash
./mvnw clean package
docker build -t devsecops-lab .
docker run --rm -p 8080:8080 devsecops-lab
```

## Integração contínua

O GitHub Actions executa em eventos de `push` e `pull_request`, configura o Java 21 e executa build e testes com Maven Wrapper. Os resultados podem ser consultados na aba **Actions** do repositório.
