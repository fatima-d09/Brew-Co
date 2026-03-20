# Brew & Co — Barista Cafe App

A full-stack Java application demonstrating **web development**, **backend architecture**,
**CRUD operations**, **database design**, and **REST API communication**.

---

## Tech Stack

| Layer       | Technology                        |
|-------------|-----------------------------------|
| Language    | **Java 17**                       |
| Framework   | **Spring Boot 3.2**               |
| Database    | **H2** (dev) / PostgreSQL (prod)  |
| ORM         | **Spring Data JPA / Hibernate**   |
| Frontend    | **HTML + CSS + Vanilla JS**       |
| Build       | **Maven**                         |

---

## Project Structure

```
cafe/
├── pom.xml
└── src/main/
    ├── java/com/cafe/
    │   ├── CafeApplication.java          ← Spring Boot entry point
    │   ├── DataSeeder.java               ← Seeds demo orders on startup
    │   ├── model/
    │   │   └── Order.java                ← JPA entity + all enums
    │   ├── dto/
    │   │   ├── OrderRequest.java         ← Request body for POST /api/orders
    │   │   └── StatusUpdateRequest.java  ← Body for PATCH /api/orders/{id}/status
    │   ├── repository/
    │   │   └── OrderRepository.java      ← Spring Data JPA queries
    │   ├── service/
    │   │   └── OrderService.java         ← Business logic layer
    │   └── controller/
    │       ├── OrderController.java      ← REST endpoints
    │       └── GlobalExceptionHandler.java
    └── resources/
        ├── application.properties        ← DB + server config
        └── static/
            └── index.html                ← Full frontend (customer + barista + admin)
```

---

## Running the App

### Prerequisites
- Java 17+
- Maven 3.6+

### Start
```bash
cd cafe
mvn spring-boot:run
```

Then open: **http://localhost:8080**

### H2 Console (inspect the database in dev)
Open: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:cafedb`
- Username: `sa`
- Password: *(empty)*

---

## REST API Reference

| Method   | Endpoint                      | Description                          |
|----------|-------------------------------|--------------------------------------|
| `POST`   | `/api/orders`                 | Place a new order                    |
| `GET`    | `/api/orders`                 | Get all orders (admin)               |
| `GET`    | `/api/orders/active`          | Get non-served orders (barista queue)|
| `GET`    | `/api/orders/{id}`            | Get single order                     |
| `PATCH`  | `/api/orders/{id}/status`     | Update order status                  |
| `DELETE` | `/api/orders/{id}`            | Cancel an order                      |
| `GET`    | `/api/orders/enums`           | All valid enum values                |

### Example — Place an Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Alex",
    "drinkType": "LATTE",
    "temp": "ICED",
    "milk": "OAT",
    "milkAerated": false,
    "flavour": "BROWN_SUGAR",
    "specialInstructions": "Light ice"
  }'
```

### Example — Update Status
```bash
curl -X PATCH http://localhost:8080/api/orders/1/status \
  -H "Content-Type: application/json" \
  -d '{ "status": "IN_PROGRESS" }'
```

---

## Order Status Pipeline

```
PENDING → IN_PROGRESS → AERATION → READY → SERVED
                    ↘              ↗
                      (skip if no foam)
                    
Any stage → CANCELLED
```

---

## Drinks Menu

**Bases:** Latte · Cappuccino · Flat White · Americano · Macchiato

**Temperature:** Hot · Iced

**Milk:** Whole · 2% · Oat · Almond · Coconut · Soy

**Aeration:** Microfoam / steamed (toggle)

**Syrups:** Vanilla · Caramel · Brown Sugar · Dark Caramel · Mocha · White Mocha ·
Peppermint · Peppermint Mocha · Peppermint White Mocha

---

## Switching to PostgreSQL

In `application.properties`, comment out the H2 block and uncomment:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cafedb
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

And add to `pom.xml` dependencies:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

---

## What This Demonstrates

| Concept               | Where                                          |
|-----------------------|------------------------------------------------|
| REST API design       | `OrderController.java` — 6 endpoints           |
| Service layer pattern | `OrderService.java` — business logic separated |
| JPA / ORM             | `Order.java` entity + `OrderRepository.java`   |
| Database CRUD         | Create, Read (list/single), Update, Delete     |
| DTO validation        | `@Valid`, `@NotBlank`, `@NotNull`              |
| Error handling        | `GlobalExceptionHandler.java`                  |
| Frontend ↔ API        | `fetch()` in `index.html` calling REST         |
| Enum-based state      | Order status pipeline as a Java enum           |
