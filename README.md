# Stock Viewer App

A minimal Spring Boot REST API to view and manage stocks.

- Spring Boot: 3.4.5
- Java: 25 (OpenJDK)
- Build: Maven 3.9+

## Prerequisites

- Java 25 installed and active on your shell
- Maven 3.9+ installed

Check your versions:

```bash
java -version
mvn -v
```

On macOS, if you have multiple JDKs installed, you can select Java 25:

```bash
# Show installed JDKs
/usr/libexec/java_home -V

# Use Java 25 for current shell
export JAVA_HOME=$(/usr/libexec/java_home -v 25)
export PATH="$JAVA_HOME/bin:$PATH"
```

## Build

```bash
# Fast build (skips tests)
mvn -DskipTests=true clean package

# Full build with tests
mvn clean test package
```

## Run

```bash
mvn spring-boot:run
```

The app starts on <http://localhost:8080>.

## API Documentation

Interactive API documentation is available via Swagger UI:

- **Swagger UI**: <http://localhost:8080/swagger-ui/index.html>
- **OpenAPI JSON**: <http://localhost:8080/api-docs>

The Swagger UI provides:

- Interactive API testing
- Complete endpoint documentation
- Request/response schema definitions
- Example values for all fields

## API Endpoints

- GET `/api/stocks/{symbol}` — get stock by symbol (from BrApi)

Example requests:

```bash
# Get one stock
curl -s http://localhost:8080/api/stocks/PETR4 | jq
```

## Configuration

Application configuration lives in `src/main/resources/application.yml`.

Common settings:

```yaml
server:
  port: 8080
logging:
  level:
    com.example.stockviewer: INFO
    org.springframework.web: INFO

# External data provider (BrApi)
brapi:
  base-url: https://brapi.dev
  token: ${BRAPI_TOKEN:}  # Optional: provide via environment variable
```

**Token setup** (optional, but recommended for higher rate limits):

The application automatically includes the token in all `/api/quote` calls as a query parameter when configured.

```bash
# Set the token as an environment variable
export BRAPI_TOKEN=your_brapi_token_here

# Then start the application
mvn spring-boot:run
```

**Without token:** Free tier with rate limits  
**With token:** Higher rate limits and faster response times

You'll see these log messages:

- **With token:** `Using BrApi token for authentication`
- **Without token:** `No BrApi token configured - using free tier with rate limits`

## Project Structure

```text
src/
  main/
    java/com/example/stockviewer/
      StockViewerApplication.java        # App entrypoint
      brapi/
        BrApiClient.java                 # HTTP client to brapi.dev
        BrApiPort.java                   # Port interface for testability
        BrApiProperties.java             # @ConfigurationProperties binding
      config/
        GlobalExceptionHandler.java      # Centralized error handling
        ErrorResponse.java               # Error response DTO
      stock/
        Stock.java                       # Domain model
        StockDTO.java                    # API DTO + validation
        StockService.java                # Business logic (in-memory cache)
        StockController.java             # REST endpoints
    resources/
      application.yml                    # App config
  test/
    java/com/example/stockviewer/
      StockViewerApplicationTests.java   # Context load test
      stock/StockServiceTest.java        # Service unit tests
```

## Testing

```bash
mvn test
```

## Troubleshooting

- Non-resolvable parent POM
  - Ensure the `<parent>` comes before the project GAV and uses:
    

    ```xml
    <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>3.4.5</version>
      <relativePath/>
    </parent>

    ```
  
  - Verify internet access to Maven Central.

- Java 25 toolchain
  - This project uses Java 25 via the Maven compiler `release` option. If compilation fails, make sure your active JDK is 25 and `JAVA_HOME` points to it.

- Lombok
  - Lombok is not used to avoid JDK 25 annotation processing issues. POJOs are implemented with plain constructors/getters/setters. If you reintroduce Lombok, ensure your toolchain supports it on Java 25.

- BrApi requests
  - GET `/api/stocks/{symbol}` fetches a single stock by symbol from BrApi.
  - If you have a BrApi token, export `BRAPI_TOKEN` to increase rate limits.

- Maven warnings referencing `sun.misc.Unsafe`
  - These are emitted by Maven internals (Guice) on JDK 25 and are harmless for normal builds. They may disappear in future Maven releases.

## Next Steps

- Add persistence (Spring Data) and a database if needed
- Add OpenAPI/Swagger via springdoc for API documentation
- Add CI (GitHub Actions) for build and tests

---

Made with Spring Boot and Java 25.
