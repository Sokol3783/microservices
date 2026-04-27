# AGENTS.md

## Architecture Overview

This is a microservices-based order management system consisting of three independent Spring Boot services:

- **order-service** (port 8081): Manages orders and order items with H2 in-memory database "orders"
- **payment-service** (port 8082): Handles payments linked to orders via orderNumber, H2 "Payments"
- **delivery-service** (port 8083): Manages deliveries tied to orders by orderNumber, H2 "Delivery"

Each service uses Spring Boot 4.0.5, Java 21, JPA with Hibernate, H2 console enabled at `/h2-console`, and OpenAPI documentation.

Services are currently independent with no inter-service communication, but entities reference `orderNumber` for logical linking.

## Key Components

- **Entities**: Order (with OrderItems), Payment, Delivery - all embed Customer value object
- **DTOs**: Records for input validation (e.g., DeliveryDTO requires @NotNull Customer)
- **Controllers**: RESTful CRUD at `/api/v1/{resource}` with standard GET/POST/PUT/DELETE
- **Services**: Business logic layer with mapping from DTOs to entities
- **Repositories**: JPA repositories for data access

## Developer Workflows

- **Build**: `./gradlew build` in each service directory
- **Run**: `./gradlew bootRun` to start a service (e.g., order-service on 8081)
- **Test**: `./gradlew test` runs JUnit 5 tests
- **Debug**: H2 console accessible at `http://localhost:{port}/h2-console` with JDBC URL `jdbc:h2:mem:{dbName}`
- **API Testing**: Use Postman collections in `src/main/resources/{service}_collection.json`

## Project Conventions

- **Package Structure**: `com.example.service{N}.{service_name}/`
- **Lombok Usage**: @Data/@Builder for entities, @RequiredArgsConstructor for services
- **Validation**: @Valid on DTOs, custom messages like "Min value have to be 1"
- **Address Fallback**: In DeliveryService, if address null, use customer.getAddress()
- **Status Enums**: DeliveryStatus (PACKING, SHIPPED, DELIVERED), PaymentStatus (PAID, PENDING, REJECTED)
- **Sequences**: @SequenceGenerator with allocationSize=10 for IDs
- **Tables**: Custom names like "_orders", "order_items", "payments", "delivery"

## Integration Points

- No external APIs or messaging yet
- Each service exposes REST endpoints independently
- Postman collections demonstrate usage scenarios, including validation tests

## Examples

- Creating an order: POST `/api/v1/orders` with OrderDTO(customer, amount, Set<OrderItemDTO>)
- Delivery address logic: If address null in DeliveryDTO, fallback to customer.address
- Validation: @NotNull on Customer in DeliveryDTO triggers 400 if null</content>
<parameter name="filePath">C:\Users\Anton\Documents\IDE\microservices\AGENTS.md
