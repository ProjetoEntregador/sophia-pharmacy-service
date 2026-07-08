# Sophia Pharmacy Service

Backend de serviço de farmácia construído com Spring Boot, Java 21 e Maven. Oferece persistência de dados, autenticação, auditoria, envio de e-mail e monitoramento com PostgreSQL, Flyway e exportador de métricas PostgreSQL compatível com Prometheus.

## Visão Geral do Projeto

- Spring Boot 3.5.6
- Java 21
- Build com Maven
- PostgreSQL para armazenamento de dados em produção
- H2 disponível para execução/testes locais
- Migrações de banco de dados com Flyway
- Autenticação baseada em JWT e suporte a Google OAuth
- Envio de e-mail via Spring Boot Mail
- Suporte a exportador, backup, manutenção e envio de logs com Docker Compose

## Pré-requisitos

- JDK Java 21
- Maven ou o wrapper Maven incluído (`./mvnw` / `mvnw.cmd`)
- Docker
- Docker Compose
- Rede Docker externa chamada `net1` ou ajuste `docker-compose.yml`

## Variáveis de Ambiente

O projeto depende de variáveis de ambiente definidas em um arquivo `.env`. Exemplos:

- `DATASOURCE_URL`
- `DATASOURCE_USERNAME`
- `DATASOURCE_PASSWORD`
- `JWT_SECRET`
- `GOOGLE_CLIENT_ID`
- `MAIL_SENDER_USERNAME`
- `MAIL_APP_PASSWORD`
- `MEDICATION_URL`
- `CORS_ALLOWED_ORIGINS`

> Observação: `docker-compose.yml` já injeta essas variáveis nos containers.

## Build

A partir da raiz do projeto:

```bash
./mvnw clean package -DskipTests
```

Ou com Maven instalado:

```bash
mvn clean package -DskipTests
```

## Executar Localmente

### Opção 1: Executar com Maven

```bash
./mvnw spring-boot:run
```

### Opção 2: Executar o JAR empacotado

```bash
java -jar target/sophia-pharmacy-service-0.0.1-SNAPSHOT.jar
```

## Docker Compose

Inicie a stack completa:

```bash
docker compose up --build
```

Esta composição inicia:

- `pharmacy-postgres`: banco de dados PostgreSQL
- `pharmacy-service`: aplicação Spring Boot
- `pharmacy-postgres-exporter`: exportador Prometheus para PostgreSQL
- `pharmacy-backup-service`: backups periódicos via scripts montados
- `pharmacy-maintenance-service`: tarefas de manutenção periódicas
- `pharmacy-promtail`: envio de logs do PostgreSQL

### Observações

- O arquivo Compose espera uma rede Docker externa chamada `net1`.
- Os dados do PostgreSQL são persistidos em `pharmacy_postgres_data`.
- Scripts de inicialização são carregados de `./db/init`.

## Migrações de Banco de Dados

As migrações do Flyway estão definidas em `src/main/resources/db/migration/V1__create_tables.sql`.

## Testes

Execute os testes de unidade com:

```bash
./mvnw test
```

## Comandos Úteis

```bash
./mvnw clean
./mvnw test
./mvnw spring-boot:run
```

## Estrutura do Projeto

- `src/main/java`: código-fonte da aplicação
- `src/main/resources`: configurações do Spring Boot
- `db/init`: scripts de inicialização do PostgreSQL
- `postgres-exporter`: configuração de consultas do exportador
- `promtail`: configuração do Promtail
- `scripts`: scripts de backup e manutenção

## Observações

- Se não houver arquivo `.env`, crie-o a partir das configurações do seu ambiente antes de iniciar o Docker Compose.
- Ajuste `docker-compose.yml` caso sua rede Docker ou caminho de logs seja diferente.
