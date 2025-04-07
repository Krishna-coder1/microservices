## Services Overview

* **`discovery-server`**: A Spring Cloud Eureka Server that acts as a service discovery registry. Microservices register themselves with this server, allowing other services to discover their locations dynamically.
* **`user-service`**: A Spring Boot microservice responsible for managing user-related data and operations.
* **`apigateway`**: A Spring Cloud Gateway instance that acts as the single entry point for external requests. It routes requests to the appropriate backend services, handles cross-cutting concerns, and can provide API management functionalities.
* **`payment-service`**: A Spring Boot microservice dedicated to processing payment transactions.
* **`notification-service`**: A Spring Boot microservice responsible for sending notifications to users or other systems. Based on the project context, it likely uses **Kafka** for asynchronous message handling.
* **`admin-server`**: A Spring Boot Admin Server that provides a web-based UI for monitoring and managing the other Spring Boot applications in the microservices ecosystem.

## Technologies Used

* **Spring Boot:** The foundation for building the individual microservices.
* **Spring Cloud:** A suite of tools for building cloud-native applications, including:
    * **Eureka (Discovery Server):** For service discovery.
    * **Gateway (API Gateway):** For API routing.
    * **Admin Server:** For monitoring.
* **Kafka:** A distributed streaming platform likely used for asynchronous communication, especially for the `notification-service`.
* **Maven:** For project build and dependency management.

## Getting Started

These instructions assume you have **Java Development Kit (JDK)** and **Maven** installed.

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/Krishna-coder1/microservices.git](https://www.google.com/search?q=https://github.com/Krishna-coder1/microservices.git)
    cd microservices
    ```

2.  **Build All Services:**
    From the root directory of the project, run the following command to build all the modules:
    ```bash
    ./mvnw clean install
    ```

3.  **Run the Services (Order Matters):**

    * **Start the Discovery Server:**
        ```bash
        cd discovery-server
        java -jar target/*.jar
        ```
        Access the Eureka dashboard typically at `http://localhost:8761`.

    * **Start the Admin Server:**
        ```bash
        cd admin-server
        java -jar target/*.jar
        ```
        Access the Spring Boot Admin UI typically at `http://localhost:9696`.

    * **Start the User Service:**
        ```bash
        cd user-service
        java -jar target/*.jar
        ```

    * **Start the Payment Service:**
        ```bash
        cd payment-service
        java -jar target/*.jar
        ```

    * **Start the Notification Service:**
        ```bash
        cd notification-service
        java -jar target/*.jar
        ```
        **Note:** Ensure you have a running Kafka instance configured for the `notification-service` to function correctly. Check the service's configuration for Kafka connection details.

    * **Start the API Gateway:**
        ```bash
        cd apigateway
        java -jar target/*.jar
        ```
        The API Gateway is usually accessible at `http://localhost:8080`.

4.  **Accessing the Application (Example):**

    You will typically interact with the application through the **API Gateway**. The specific endpoints will depend on how the gateway is configured to route requests to the backend services. Refer to the `apigateway` project's configuration (e.g., `application.yml`) for routing rules.

    Examples might include:

    * Accessing user-related endpoints via a path like `/users/**`
    * Accessing payment-related endpoints via a path like `/payments/**`

    The `notification-service` likely operates in the background, consuming Kafka topics and sending notifications.

## Further Information

* Explore the source code within each service to understand its specific functionality and API endpoints.
* Examine the `application.properties` or `application.yml` files in each service for configuration details, including server ports and service discovery settings.
* Refer to the Spring Cloud documentation for more information on Eureka, Gateway, and Admin Server.
* If you want to understand the asynchronous communication, investigate the `notification-service` for Kafka producer and consumer configurations.
