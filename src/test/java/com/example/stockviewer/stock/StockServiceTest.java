package com.example.stockviewer.stock;

import com.example.stockviewer.brapi.BrApiPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockServiceTest {

    private StockService stockService;

    @BeforeEach
    void setUp() {
        BrApiPort fake = new BrApiPort() {
            @Override
            public List<Stock> getQuotes(List<String> symbols) {
                return List.of(
                        new Stock("PETR4", "Petrobras PN", BigDecimal.valueOf(38.50), BigDecimal.valueOf(37.80), null)
                );
            }

            @Override
            public Stock getQuote(String symbol) {
                if ("PETR4".equalsIgnoreCase(symbol)) {
                    return new Stock("PETR4", "Petrobras PN", BigDecimal.valueOf(38.50), BigDecimal.valueOf(37.80), null);
                }
                return null;
            }
        };
        stockService = new StockService(fake);
    }

    @Test
    void getStockBySymbol_WithValidSymbol_ShouldReturnStock() {
        Stock stock = stockService.getStockBySymbol("PETR4");
        assertNotNull(stock);
        assertEquals("PETR4", stock.getSymbol());
        assertEquals("Petrobras PN", stock.getName());
    }

    @Test
    void getStockBySymbol_WithInvalidSymbol_ShouldReturnNull() {
        Stock stock = stockService.getStockBySymbol("INVALID");
        assertNull(stock);
    }
}
