package com.example.stockviewer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger configuration for the Stock Viewer API.
 * Provides interactive API documentation accessible at /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private int serverPort;

    @Bean
    public OpenAPI stockViewerOpenAPI() {
        final Server localServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Local development server");

        final Info apiInfo = new Info()
                .title("Stock Viewer API")
                .description("REST API for viewing Brazilian stock market data via BrApi integration")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Stock Viewer Team")
                        .url("https://github.com/marcelotmnobrega/stock-viewer-app"))
                .license(new License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT"));

        return new OpenAPI()
                .info(apiInfo)
                .servers(List.of(localServer));
    }
}
