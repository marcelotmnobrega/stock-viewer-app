package com.example.stockviewer.brapi;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "brapi")
public class BrApiProperties {
    private String baseUrl = "https://brapi.dev";
    private String token;
    private List<String> defaultSymbols = new ArrayList<>(List.of("PETR4", "VALE3", "ITUB4"));

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getDefaultSymbols() {
        return defaultSymbols;
    }

    public void setDefaultSymbols(List<String> defaultSymbols) {
        this.defaultSymbols = defaultSymbols;
    }
}
