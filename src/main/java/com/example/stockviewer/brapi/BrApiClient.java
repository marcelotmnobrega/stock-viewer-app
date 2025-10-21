package com.example.stockviewer.brapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.stockviewer.stock.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class BrApiClient implements BrApiPort {

    private static final Logger logger = LoggerFactory.getLogger(BrApiClient.class);

    private final RestClient restClient;
    private final BrApiProperties properties;

    public BrApiClient(BrApiProperties properties) {
        this.properties = properties;
        this.restClient = RestClient.builder()
                .baseUrl(properties.getBaseUrl())
                .defaultHeader("Accept", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public List<Stock> getQuotes(List<String> symbols) {
        if (symbols == null || symbols.isEmpty()) {
            symbols = properties.getDefaultSymbols();
        }

        String tickers = String.join(",", symbols);
        String tokenQuery = properties.getToken() != null && !properties.getToken().isBlank()
                ? "&token=" + properties.getToken() : "";

        String path = "/api/quote/" + tickers + "?range=1d" + tokenQuery;

        logger.debug("Requesting BrApi quotes: {}{}", properties.getBaseUrl(), path);

        QuoteResponse response = restClient.get()
                .uri(path)
                .retrieve()
                .body(QuoteResponse.class);

        List<Stock> results = new ArrayList<>();
        if (response != null && response.results != null) {
            for (QuoteResult r : response.results) {
                // Map fields conservatively; many are optional
                String symbol = r.symbol;
                String name = r.longName != null ? r.longName : r.shortName;
                BigDecimal current = r.regularMarketPrice != null ? r.regularMarketPrice : r.price;
                BigDecimal prevClose = r.regularMarketPreviousClose != null ? r.regularMarketPreviousClose : r.previousClose;
                LocalDateTime updated = LocalDateTime.now();

                if (symbol == null || current == null || prevClose == null) {
                    logger.warn("Skipping incomplete quote: {}", r);
                    continue;
                }

                Stock s = new Stock();
                s.setSymbol(symbol);
                s.setName(name);
                s.setCurrentPrice(current);
                s.setPreviousClose(prevClose);
                s.setLastUpdated(updated);
                results.add(s);
            }
        }
        return results;
    }

    @Override
    public Stock getQuote(String symbol) {
        List<Stock> stocks = getQuotes(List.of(symbol));
        return stocks.stream()
                .filter(s -> Objects.equals(symbol.toUpperCase(), s.getSymbol()))
                .findFirst()
                .orElse(null);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class QuoteResponse {
        @JsonProperty("results")
        public List<QuoteResult> results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class QuoteResult {
        public String symbol;
        public String shortName;
        public String longName;
        public BigDecimal price;
        public BigDecimal regularMarketPrice;
        public BigDecimal previousClose;
        public BigDecimal regularMarketPreviousClose;

        @Override
        public String toString() {
            return "QuoteResult{" +
                    "symbol='" + symbol + '\'' +
                    ", shortName='" + shortName + '\'' +
                    ", longName='" + longName + '\'' +
                    ", price=" + price +
                    ", regularMarketPrice=" + regularMarketPrice +
                    ", previousClose=" + previousClose +
                    ", regularMarketPreviousClose=" + regularMarketPreviousClose +
                    '}';
        }
    }
}
