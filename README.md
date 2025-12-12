# 🏷️ Coupon API – Desafio Técnico Jr

Este projeto é uma **API REST** desenvolvida com **Spring Boot** para gerenciamento de cupons de desconto, implementando todas as regras de negócio propostas no desafio técnico.

---

## 📌 Objetivo do Desafio

Criar uma API para **criação e exclusão (soft delete)** de cupons, garantindo:

* Validações de negócio
* Persistência em banco em memória
* Cobertura de testes
* Boas práticas com Spring Boot

---

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **Bean Validation**
* **H2 Database (em memória)**
* **JUnit 5 & Mockito**
* **Maven**

---

## 📦 Dependências Principais

As dependências abaixo foram geradas via **Spring Initializr**:

* `spring-boot-starter-web`
* `spring-boot-starter-data-jpa`
* `spring-boot-starter-validation`
* `spring-boot-starter-test`
* `h2`

---

## 🧩 Regras de Negócio Implementadas

### ✔️ Criação de Cupom

* Campos obrigatórios:

    * `code`
    * `description`
    * `discountValue`
    * `expirationDate`
* O código do cupom:

    * Deve conter **6 caracteres alfanuméricos**
    * Pode receber caracteres especiais na entrada, mas eles são **removidos antes de salvar**
* Valor mínimo de desconto: **0.5**
* Não é permitido criar cupom com data de expiração no passado
* O cupom pode ser criado como **publicado**

---

### 🗑️ Exclusão de Cupom (Soft Delete)

* O cupom pode ser deletado a qualquer momento
* A exclusão é feita via **soft delete** (registro não é removido do banco)
* Não é permitido deletar um cupom já deletado

---

## 🗃️ Banco de Dados

* Banco utilizado: **H2 em memória**
* Console H2 disponível em:

```
http://localhost:8080/h2-console
```

Configuração padrão:

* JDBC URL: `jdbc:h2:mem:testdb`
* User: `sa`
* Password: *(vazio)*

---

## 🔗 Endpoints Principais

### ➕ Criar Cupom

```http
POST /coupons
```

**Request Body (exemplo):**

```json
{
  "code": "AB@C-12",
  "description": "Cupom de desconto",
  "discountValue": 10.0,
  "expirationDate": "2025-12-31",
  "published": true
}
```

---

### ❌ Deletar Cupom

```http
DELETE /coupons/{id}
```

---

## 🧪 Testes

O projeto contém testes cobrindo as principais regras de negócio:

* Testes de **Service** (JUnit + Mockito)

Para executar os testes:

```bash
mvn test
```

---

## ▶️ Executando o Projeto

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080/cupom-api
```

---

## 📁 Estrutura do Projeto (resumida)

```
src
 └── main
     └── java
         └── com.example.coupon
             ├── controller
             ├── service
             ├── repository
             └── dto
 └── test
```

---

## 📄 Documentação da API

A documentação de referência do desafio está disponível em:

🔗 [https://n1m0i5k0zu.apidog.io/](https://n1m0i5k0zu.apidog.io/)

---

## 📌 Observações Finais

Este projeto foi desenvolvido com foco em:

* Clareza de regras de negócio
* Simplicidade
* Boas práticas com Spring Boot
* Cobertura de testes

---

👤 **Autor:** *George Felipe*
📎 **Repositório:** *https://github.com/georgefelipee/cupom-api*
