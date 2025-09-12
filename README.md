# 📌 API Rest - Encurtador de URL com Spring Framework (Java)

## 📖 Descrição
Este projeto é um Encurtador de URLs desenvolvido em Spring Boot (Java 21). A ideia é simples: pegar uma URL muito grande e criar um link curto, que redireciona para a URL original.

Ele já tem algumas funcionalidades importantes, como a persistência dos dados, limpeza de links expirados e estatísticas de acesso diário.
---

## 🛠️ Tecnologias (Dev)
 - ☕ Java 21
 - 🍃 Spring Boot 3
 - 🗄 Spring Data JPA
 - 🔀 Flyway (migrations)
 - 🧪 H2 Database (desenvolvimento)
 - 🐬 MySQL (produção)
 - 📑 Swagger / OpenAPI (documentação da API)
 - 📦 Maven

---

## ⚙️ Funcionalidades
- [x] Criar um link encurtado a partir de uma URL original.  
- [x] Redirecionar usuários para a URL original via código curto.  
- [x] Expiração de links de baixo acesso (até 7 dias).
- [x] Renovação do prazo de expiração dos links.
- [x] Estatísticas de acessos diários.  
- [ ] Autenticação e gestão de usuários (futuro).  

---

## 🔗 Endpoints

### 🔹 Criar uma URL encurtada
`POST /api`  
- **Descrição:** Cria uma URL curta a partir de uma URL original.  
- **Parâmetros:**  
  - `originalUrl` *(obrigatório)*: A URL original a ser encurtada.  

**Exemplo de requisição:**

POST /?originalUrl=https://www.example.com

**Resposta de sucesso:**

```
  {
    "id": 1,
    "originalUrl": "https://www.example.com",
    "shortCode": "7429ahje",
    "createdAt": "2025-09-03T18:27:58.309Z",
    "expiresAt": "2025-09-10T18:27:58.309Z",
    "accesses": null
  }
```

**Resposta de error:**

```
  {
    "status": 500,
    "message": "Internal Error: Error generating URL code",
    "timestamp": "2025-09-03T15:49:41.900743"
  }
```

### 🔹 Redirecionar para a URL original
`GET /api/{code}`  
- Descrição: Redireciona o usuário para a URL original por meio do código curto.
- Parâmetros:
    - `code` (obrigatório): O código curto que representa a URL.
  
**Exemplo de requisição:**

  GET /65tyui98

**Resposta de sucesso:**

> 302 Found com header Location: https://www.example.com

**Resposta de error:**

```
  {
    "status": 404,
    "message": "URL short not found",
    "timestamp": "2025-09-03T15:49:41.900743"
  }
```


### 🔹Estatísticas de acesso diário
`GET /api/stats/{code}`  
- Descrição: Retorna estatísticas de acesso diário de um link curto.
- Parâmetros:
    - `code` (obrigatório): O código curto que representa a URL.
  
**Exemplo de requisição:**
  GET /stats/65tyui98

**Resposta de sucesso:**

```
  {
    "id": 1,
    "originalUrl": "https://www.example.com",
    "shortCode": "7b29aace",
    "createdAt": "2025-09-03T15:27:58.222662-03:00",
    "expiresAt": "2025-09-10T15:27:58.222662-03:00",
    "accesses": [
      {
        "id": 1,
        "accessDate": "2025-09-03",
        "accessCount": 1
      }
    ],
    "totalAccess": 1,
    "lastAccess": "2025-09-03"
  }
```

**Resposta de error:**

```
  {
    "status": 404,
    "message": "URL short not found",
    "timestamp": "2025-09-03T15:49:23.7422423"
  }
```

## Documentação do Swagger

A documentação da API pode ser acessada em: [Documentação do Swagger](http://localhost:8080/swagger-ui/index.html#/).

## Possíveis Melhorias

- Deploy definitivo utilizando serviços da AWS.
- Criar Dockerfile e docker-compose para simplificar a implantação e execução em ambientes de contêiner.
  
## Instalação 

1. Clonar repositório: git clone https://github.com/henrique/url-shortener.git 
2. Rodar aplicação com maven: ./mvnw spring-boot:run
3. Acessar banco de dados em memória: http://localhost:8080/h2-console

## Licença

Projeto desenvolvido para fins de aprendizado em Java, Spring Boot e AWS.

Este projeto está licenciado sob a licença MIT. Consulte o
arquivo <a href="https://github.com/henriquef9/url-shortener/blob/main/LICENSE">(LICENSE)</a> para obter.
