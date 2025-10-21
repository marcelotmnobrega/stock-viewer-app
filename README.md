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

## API Endpoints

- GET `/api/stocks` — list all stocks
- GET `/api/stocks/{symbol}` — get stock by symbol
- POST `/api/stocks` — create stock
- PUT `/api/stocks/{symbol}` — update stock
- DELETE `/api/stocks/{symbol}` — delete stock

Example requests:

```bash
# List all
curl -s http://localhost:8080/api/stocks | jq

# Get one
curl -s http://localhost:8080/api/stocks/AAPL | jq

# Create
curl -s -X POST http://localhost:8080/api/stocks \
  -H 'Content-Type: application/json' \
  -d '{
        "symbol":"TSLA",
        "name":"Tesla Inc.",
        "currentPrice":250.00,
        "previousClose":245.00
      }' | jq
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
```

## Project Structure

```
src/
  main/
    java/com/example/stockviewer/
      StockViewerApplication.java        # App entrypoint
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

- Maven warnings referencing `sun.misc.Unsafe`
  - These are emitted by Maven internals (Guice) on JDK 25 and are harmless for normal builds. They may disappear in future Maven releases.

## Next Steps

- Add persistence (Spring Data) and a database if needed
- Add OpenAPI/Swagger via springdoc for API documentation
- Add CI (GitHub Actions) for build and tests

---

Made with Spring Boot and Java 25.
