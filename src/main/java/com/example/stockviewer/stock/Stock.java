package com.example.stockviewer.stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class Stock {

    private String symbol;
    private String name;
    private BigDecimal currentPrice;
    private BigDecimal previousClose;
    private LocalDateTime lastUpdated;

    public Stock() {
    }

    public Stock(String symbol, String name, BigDecimal currentPrice, BigDecimal previousClose, LocalDateTime lastUpdated) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
        this.previousClose = previousClose;
        this.lastUpdated = lastUpdated;
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
        if (previousClose == null || currentPrice == null) {
            return BigDecimal.ZERO;
        }
        return currentPrice.subtract(previousClose);
    }

    public BigDecimal getPriceChangePercentage() {
        if (previousClose == null || previousClose.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getPriceChange()
                .divide(previousClose, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
