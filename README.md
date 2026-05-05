# 🧬 The Project BioSecure

---

## What is BioSecure?

BioSecure is a fictional B2B company that works with products and services in biosafety and biotechnology areas.
The target customers are hospitals, clinics, pharmacies and laboratories.

### 📦 Products:

The products can be divided into 4 main groups:

1. **Safety:** PPE's (Personal Protective Equipments), always required in laboratory environments.
2. **Apparatus:** Every-day laboratory bench equipments and instruments.
3. **Chemicals:** Primarily used in cleaning processes, sterilization and environmental control.
4. **Biologicals:** products related to microbiological research and analysis.

With these 4 groups, BioSecure offers its customers a comprehensive buying experience, allowing them to find all the necessary supplies for a complete and safe laboratory workflow in a single place.

### 🎯 Mission, Vision and Values

- **Mission:** To empower healthcare and scientific professionals by providing reliable, high-quality biosafety equipment and solutions for their daily operations.


- **Vision:** To become the global B2B standard in laboratory safety and biotechnological supply chains.


- **Values:**
  - Uncompromising Safety: Protecting lives and scientific integrity above all else.

  - Domain Accuracy: Understanding the deep technical needs of our partners.

  - Continuous Innovation: Evolving our solutions alongside modern science.

---

## ⚙️ Project's Architecture, Design and Tech Stack

### Architecture:

The system architecture is a **distributed system**, based on **containerized microservices.** Furthermore, the services adhere to **Clean Architecture** and **Domain Driven Design (DDD)** principles, ensuring the strict decoupling of business logic and infrastructure details.

Detailed information regarding the decisions behind adopting these patterns is available in the **ADRs (Architectural Decision Records)** hosted within this hub.

### Design Patterns:

Key patterns utilized across the ecosystem include:

- Notification Pattern: For robust and accumulative domain validations.

- Builders & Test Data Builders: To facilitate complex object instantiation and maintainable testing.

- DTO (Data Transfer Object): To securely and predictably transfer data among layers and external services.

### Tech Stack:

<div>
  <img src="https://skillicons.dev/icons?i=java,spring,maven,postgres,docker&perline=10"  alt="The icons of the technologies utilized on the project"/>
</div>


> Note: more ADRs coming soon.

---

## 🧭 Main Hub Contents

This repository serves as the BioSecure's Main Hub, meaning it does not contain direct application source code.

Here you will find high-level architectural documentation, setup scripts to configure the ecosystem as a whole, Architectural Decision Records (ADRs), and direct links to the individual microservices repositories.

---

## 🌱 The Ecosystem

[📦 Products API](https://github.com/MaiteALC/biosecure-products-api) - The RESTful service responsible for handling all product-catalog operations.

[🤝 Customers API](https://github.com/MaiteALC/biosecure-customers-api) - The RESTful service managing B2B partner profiles and relationships.

[🛠️ Commons lib](https://github.com/MaiteALC/biosecure-commons-lib) - A internal shared library providing core utility methods and domain abstractions.

---

## 📈 History & Evolution

**Previous Architecture:** Monorepo

**Current Architecture:** Microservices/Poly-repo.

BioSecure started as a monolith, with distinct packages isolating their respective domain classes. This approach made the initial development phase much easier and highly centralized.

As business complexity increased, the packages were refactored into distinct modules. At this point, the project became a multi-module monolith orchestrated by a parent pom.xml.

Scaling even further, the `commons-lib`, `customers-api`, and `products-api` modules were physically extracted into their own repositories. Thenceforth, containerization and independent CI/CD pipelines could be straightforwardly implemented.

If you want to explore the original directory structure and early evolution, you can browse the multi-module monolithic era snapshot by [clicking here](https://github.com/MaiteALC/biosecure-hub/releases/tag/v1.0.0-monolithic-era).

---

## 🚀 Spinning up the ecosystem

`TODO: Centralized scripts (e.g., Makefiles, global Docker Compose) dedicated to spinning up the entire ecosystem concurrently are under development.`