# Serviço de Farmácia

O **Serviço de Farmácia** é responsável pelo gerenciamento das farmácias, usuários e permissões de acesso do sistema SophIA. Além disso, realiza autenticação de usuários (credenciais e Google OAuth), gerenciamento de convites para funcionários, comunicação com o serviço de medicamentos e publicação de eventos de auditoria para rastreabilidade das operações realizadas.

---

## 🛠️ Funcionalidades

- Cadastro, consulta, atualização e remoção de farmácias
- Gerenciamento de usuários e funcionários
- Controle de permissões por farmácia (Owner e Employee)
- Login utilizando e-mail e senha
- Login com Google OAuth 2.0
- Envio e gerenciamento de convites para novos funcionários
- Consulta de farmácias próximas por geolocalização
- Integração com o Serviço de Medicamentos
- Publicação de eventos de auditoria no RabbitMQ
- Envio de e-mails para convites de usuários

---

## 💻 Tecnologias

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- RabbitMQ
- Flyway
- Docker
- Docker Compose
- Google OAuth
- JWT

---

## 🚀 Instalação

### Pré-requisitos

- Git
- Docker
- Docker Compose

---

### Clonar

```bash
git clone https://github.com/ProjetoEntregador/sophia-pharmacy-service.git
cd sophia-pharmacy-service
```

---

### Dependências do ecossistema

Para executar o serviço integrado ao restante do sistema, é necessário ter:

- RabbitMQ disponível na rede compartilhada;
  - https://github.com/ProjetoEntregador/sophia-message-broker.git
- rede Docker externa net1, utilizada para comunicação entre os microsserviços.
  ```bash
  docker network create net1
  ```
No ambiente Docker do sistema completo, os nomes esperados são:

- RabbitMQ: rabbitmq:5672;
- PostgreSQL deste serviço: pharmacy-postgres:5432.

___
### Configurar variáveis `.env`

Renomeie o arquivo `.env.example` para `.env`.

Configure as variáveis necessárias para:

- Banco de dados
- JWT
- Google OAuth
- Serviço de e-mail
- Serviço de medicamentos
- CORS

---

### Inicializando

```bash
docker-compose up -d
```

---

### Acessando a aplicação

API:

```
http://localhost:8080
```

---
