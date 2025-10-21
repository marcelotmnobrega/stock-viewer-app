package com.example.stockviewer;

import com.example.stockviewer.brapi.BrApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BrApiProperties.class)
public class StockViewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockViewerApplication.class, args);
    }
}
