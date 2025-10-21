package com.example.stockviewer.stock;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StockDTO {

    @NotBlank(message = "Symbol is required")
    private String symbol;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Current price is required")
    @Positive(message = "Current price must be positive")
    private BigDecimal currentPrice;

    @NotNull(message = "Previous close is required")
    @Positive(message = "Previous close must be positive")
    private BigDecimal previousClose;

    private LocalDateTime lastUpdated;
    private BigDecimal priceChange;
    private BigDecimal priceChangePercentage;

    public StockDTO() {
    }

    public StockDTO(String symbol, String name, BigDecimal currentPrice, BigDecimal previousClose,
                    LocalDateTime lastUpdated, BigDecimal priceChange, BigDecimal priceChangePercentage) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.previousClose = previousClose;
        this.lastUpdated = lastUpdated;
        this.priceChange = priceChange;
        this.priceChangePercentage = priceChangePercentage;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getPreviousClose() {
        return previousClose;
    }

    public void setPreviousClose(BigDecimal previousClose) {
        this.previousClose = previousClose;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(BigDecimal priceChange) {
        this.priceChange = priceChange;
    }

    public BigDecimal getPriceChangePercentage() {
        return priceChangePercentage;
    }

    public void setPriceChangePercentage(BigDecimal priceChangePercentage) {
        this.priceChangePercentage = priceChangePercentage;
    }
}
