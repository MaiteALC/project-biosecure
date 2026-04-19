> **Context:**

BioSecure handles critical and complex business rules about biosafety. Mixing these rules with infrastructure details, like frameworks, UI, web controllers, and databases makes the systems hard to maintain and test in the long term. We need an architecture that protects the core of the application.

> **Decision:**

Adopt Clean Architecture in conjunction with Domain-Driven Design (DDD) concepts.
The code will be divided into concentric layers, where the dependencies point only inwards (to the domain layer). Thus, we ensure that the core is protected and isolated from the external world, without the need to know about anything outside.

Furthermore, the code follows DDD concepts like ubiquitous language, aggregates, value objects, aggregate invariants. In this way the code base gains more domain alignment, especially regarding technical terms.

> **Consequences:**

- **Positive:** The business rules (entities, use cases) don't depend on external libraries or frameworks (like Spring Boot), making pure unit testing easier.

- **Positive:** Changes in database or user interface don't have collateral impact on the domain layer.

- **Negative:** Increase in initial complexity and "boilerplate code" (more classes to create, like DTOs and interfaces) to enable the communication between different layers.

- **Negative:** Slightly steeper learning curve to understand how each component fits together.