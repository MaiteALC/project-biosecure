> **Context:**

The applications of the BioSecure ecosystem operate in a critical domain, dealing with complex validations, compliance handling, sensitive data and its integrity as well as controlled products.

> **Considered choices:**

 - **Python:** simple and fast to develop, but because it has dynamic typing it may pose a risk to domain integrity. Furthermore, its performance  leaves something to be desired.
 - **C#:** highly integrated with Windows desktop environments, statically typed and not too complex. However, is not our primary choice for developing RESTful APIs and deals with web. C# will be considered for use in another parts of BioSecure.
 - **Java:** statically typed, verbose, highly used for backend web development (mainly with SpringBoot) and enterprise systems like banks with their enormous corporate ecosystem.

However, after analysis, it was concluded that we needed a programming language that offers:

1. Robustness and Static Typing: To catch errors at compile time, reducing the risk of bugs in critical business rules.
2. Mature Ecosystem: Libraries tested for persistence, security, and integration.
3. Maintainability: The system is designed to have a long lifecycle.

> **Decision:**

It was decided to use Java 21 for the main backend language.
Consequences:
- **Positive:** Java's strong typing helps us model the Domain (following the DDD) accurately, preventing invalid states.
- **Positive:** Access to the Spring ecosystem (Boot, Security, Data), a mature framework that offers several relevant functionalities.
- **Negative:** Steeper learning curve and initial configuration (boilerplate) compared to scripting languages.
- **Negative:** Slightly higher memory consumption compared to natively compiled languages (such as Go, Rust or C++), acceptable given the focus on business correctness.