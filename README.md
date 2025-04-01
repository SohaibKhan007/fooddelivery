# Food Delivery System

This project is a Food Delivery System built with Spring Boot. It provides RESTful APIs to manage menu items, orders, and users.

## Features

- Manage menu items: add, update, delete, and retrieve.
- Manage orders: place, update, and retrieve by status.
- Manage users: create, update, delete, and retrieve by ID or email.
- Global exception handling for meaningful error responses.

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- H2 Database (or any other database configured)
- Maven
- Swagger for API documentation

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.6+
- An IDE like IntelliJ IDEA or Eclipse

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/SohaibKhan007/fooddelivery.git
   cd fooddelivery
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**

   The application will be running at `http://localhost:8080`.

5. **API Documentation:**

   Access Swagger UI at `http://localhost:8080/swagger-ui.html` for API documentation.

## API Endpoints

### Menu Items

- **POST /api/menu-items**: Add a new menu item.
- **GET /api/menu-items/{id}**: Retrieve a menu item by ID.
- **GET /api/menu-items/restaurant/{restaurantId}**: Retrieve menu items by restaurant ID.
- **PUT /api/menu-items/{id}**: Update a menu item.
- **DELETE /api/menu-items/{id}**: Delete a menu item.

### Orders

- **POST /api/orders/place**: Place a new order.
- **GET /api/orders/status/{status}**: Retrieve orders by status.

### Users

- **POST /api/users**: Create a new user.
- **GET /api/users/{id}**: Retrieve a user by ID.
- **GET /api/users/email/{email}**: Retrieve a user by email.
- **GET /api/users**: Retrieve all users.
- **PUT /api/users/{id}**: Update user details.
- **DELETE /api/users/{id}**: Delete a user.

## Error Handling

The application provides detailed error messages for validation errors and handles exceptions globally to ensure consistent responses.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any improvements.
