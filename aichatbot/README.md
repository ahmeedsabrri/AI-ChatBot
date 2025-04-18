# AI ChatBot

A Spring Boot-based AI chatbot application that provides channel-based conversations with AI using OpenRouter's API. The application includes user authentication, rate limiting, and comprehensive API documentation.

## Features

- User authentication with JWT and Google OAuth2
- Channel-based conversations
- Message history with pagination and search
- Rate limiting per endpoint
- Comprehensive API documentation with OpenAPI/Swagger
- H2 in-memory database for development
- CORS configuration for frontend integration
- Exception handling and validation

## Prerequisites

- Java 21
- Maven
- Google OAuth2 credentials (for OAuth2 login)
- OpenRouter API key

## Configuration

### Application Properties

Configure the following properties in `application.properties`:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:testdb

# JWT Configuration
jwt.secret=your256bitSecretKeyHereMakeItLongAndSecureForProductionUse
jwt.expiration=86400000

# Google OAuth2 Configuration
spring.security.oauth2.client.registration.google.client-id=your-client-id
spring.security.oauth2.client.registration.google.client-secret=your-client-secret

# OpenAI Configuration
OPENROUTER_API_KEY=your-openrouter-api-key
```

## Building and Running

1. Clone the repository
2. Configure application.properties
3. Build the project:
   ```bash
   ./mvnw clean install
   ```
4. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Documentation

Once the application is running, access the Swagger UI documentation at:
- http://localhost:8080/swagger-ui.html

## API Endpoints

### Authentication

- `POST /api/v1/auth/register` - Register a new user
- `POST /api/v1/auth/login` - Login with username/password
- `GET /api/v1/auth/oauth2/callback` - OAuth2 callback handler

### Channels

- `POST /api/v1/channels` - Create a new channel
- `GET /api/v1/channels` - Get user's channels
- `GET /api/v1/channels/{id}` - Get specific channel
- `DELETE /api/v1/channels/{id}` - Delete a channel

### Messages

- `POST /api/v1/channels/{channelId}/messages` - Send a message
- `GET /api/v1/channels/{channelId}/messages` - Get channel messages

## Rate Limiting

The application implements rate limiting per endpoint:
- Message sending: 30 requests per minute
- Message retrieval: 60 requests per minute
- Channel creation/deletion: 20 requests per minute
- Channel listing/retrieval: 60 requests per minute

## Security

- JWT-based authentication
- Google OAuth2 integration
- CSRF protection
- Content Security Policy headers
- Rate limiting protection
- Input validation

## Error Handling

The application provides consistent error responses for:
- Authentication failures
- Authorization failures
- Rate limit exceeded
- Invalid input
- Resource not found
- Server errors

## Project Structure

```
src/main/java/com/ai/chatbot/aichatbot/
├── AichatbotApplication.java
├── annotation/
├── config/
├── controller/
├── dto/
├── exception/
├── interceptor/
├── mapper/
├── model/
├── repository/
├── security/
├── service/
├── util/
└── validation/
```

## Dependencies

- Spring Boot 3.4.4
- Spring Security
- Spring Data JPA
- JWT
- Google OAuth2
- OpenAPI/Swagger
- H2 Database
- Guava (Rate Limiting)
- Apache HttpClient 5
- Jackson
- Jakarta Validation

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.