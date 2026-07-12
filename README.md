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

### Configurar variáveis `.env`

Renomeie o arquivo `.env.example` para `.env`.

Configure as variáveis necessárias, como:

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
