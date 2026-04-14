# 🧬 BioSecure Customers API

This repository contains the RESTful API responsible for handling all customer-related operations. It is a core microservice that makes up the BioSecure ecosystem.

> What is BioSecure? It's an enterprise software ecosystem mimicking real-world scenarios for a fictional B2B biosafety company.

Ecosystem Links:

[📦 Products API](https://github.com/MaiteALC/biosecure-products-api)

[🛠️ Commons lib](https://github.com/MaiteALC/biosecure-commons-lib)

**Current project status:** 🚧 In Development

---

## Tech Stack & Patterns

**Main tools:**
- **Language:** Java 21
- **Framework:** SpringBoot
- **Database:** PostgreSQL
- **Build Manager:** Maven

**Architecture & Design:**

This project adheres to Clean Architecture and Domain-Driven Design (DDD) principles. By creating concentric layers, the domain layer is protected and isolated from the external world. Furthermore, the strict use of Ubiquitous Language ensures domain accuracy and alignment with business rules. 

---

## 🏛️ Bounded Context

The main class in this project is the `Customer`, an aggregate root composed of:

- 🏷️ `Cnpj`: A value object representing a Brazilian Corporate Taxpayer Registry number.
- 💰 `FinancialData`: The internal and private financial profile of a customer.
- 📍 `Address`: A value object that encapsulates the address information.
- ⚖️ `TaxData`: The public statutory and legal tax profile of a customer.

Together, these classes compose a complete and valid customer state within the BioSecure domain.

---

## 🚀 How to Run

### Local Setup Prerequisites:
* Java 21
* PostgreSQL 17

```Bash
# Clone the repository
git clone https://github.com/MaiteALC/biosecure-customers-api

# Navigate into the directory
cd biosecure-customers-api

# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

🐳 Running with Docker:

    TODO: Instructions for containerized execution will be added soon (Requires specific configuration for GitHub Packages authentication).
---

## 📖 API Documentation

    TODO: Swagger UI / OpenAPI specification integration is planned for future releases. 

---

## 🔄 Project Evolution & Poly-repo Migration
BioSecure started as a monolithic multi-module project to facilitate early development. As the project scaled, modules like `commons-lib` and `customer/products-api` were extracted into their own repositories.

**Current Architecture:** Microservices/Poly-repo.

**Previous Architecture:** Monorepo

If you want to explore the original monolithic directory structure and early evolution, you can browse the v1.0.0-monolithic-era snapshot [here](https://github.com/MaiteALC/biosecure-customers-api/releases/tag/v1.0.0-monolithic-era).