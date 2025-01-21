# ArchUnit Sample Project
This project demonstrates how to use ArchUnit to enforce architectural rules in a Java application. It includes a layered architecture with controllers, services, repositories, and models, as well as tests to validate adherence to these rules.

## Project Structure
The project follows a typical layered architecture:

```
src
├── main
│   ├── java
│   │   └── org
│   │       └── karane
│   │           └── archunit
│   │               ├── controller   // Controllers handle incoming requests
│   │               ├── service      // Services implement business logic
│   │               ├── repository   // Repositories interact with the database
│   │               └── model        // Domain models
└── test
    └── java
        └── org
            └── karane
                └── archunit
                    └── LayeredArchitectureTest.java // ArchUnit tests
```

## Key Features
- **Layered Architecture**:
  - Controllers → Services → Repositories → Models
  - Each layer depends only on the layer directly beneath it.
- **ArchUnit Tests**:
  - Enforce architectural rules.
  - Prevent unexpected dependencies or violations.
- **Custom Assertions**: Examples of field, class, method, and dependency constraints.
  
## Getting Started
### Prerequisites
Ensure you have the following installed:
- Java 8+
- Maven 3.6+

### Running the Project
1. **Clone the repository**:

```bash

git clone https://github.com/your-username/archunit-sample.git
cd archunit-sample
```

2. **Run the tests**:

```bash

mvn test
```

3. **Inspect the output**:

- If the tests pass, all architectural rules are adhered to.
- If a test fails, the output will describe the violated rule and the problematic class.

### Architectural Rules Enforced
1. **Controllers should only depend on services**:
- Controllers must not access repositories or models directly.

2. **Services should only depend on repositories and models**:
- Services must not depend on controllers or other unrelated layers.

3. **Repositories must not depend on controllers or services**:
- Repositories interact with the database and are independent of other layers.

4. **Models must be independent**:
- Models should not depend on any layer except Java core libraries.