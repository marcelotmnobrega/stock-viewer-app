package com.example.stockviewer.stock;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Stock market data transfer object")
public class StockDTO {

    @Schema(description = "Stock symbol (ticker)", example = "PETR4", required = true)
    @NotBlank(message = "Symbol is required")
    private String symbol;

    @Schema(description = "Company name", example = "Petróleo Brasileiro S.A. - Petrobras", required = true)
    @NotBlank(message = "Name is required")
    private String name;

    @Schema(description = "Current market price in BRL", example = "29.75", required = true)
    @NotNull(message = "Current price is required")
    @Positive(message = "Current price must be positive")
    private BigDecimal currentPrice;

    @Schema(description = "Previous closing price in BRL", example = "29.73", required = true)
    @NotNull(message = "Previous close is required")
    @Positive(message = "Previous close must be positive")
    private BigDecimal previousClose;

    @Schema(description = "Timestamp of last update", example = "2025-10-21T14:30:00")
    private LocalDateTime lastUpdated;
    
    @Schema(description = "Price change from previous close", example = "0.02")
    private BigDecimal priceChange;
    
    @Schema(description = "Price change percentage", example = "0.0700")
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
