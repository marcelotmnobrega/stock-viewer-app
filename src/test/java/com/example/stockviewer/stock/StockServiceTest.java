package com.example.stockviewer.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockServiceTest {

    private StockService stockService;

    @BeforeEach
    void setUp() {
        stockService = new StockService();
    }

    @Test
    void getAllStocks_ShouldReturnInitializedStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        
        assertNotNull(stocks);
        assertEquals(3, stocks.size());
    }

    @Test
    void getStockBySymbol_WithValidSymbol_ShouldReturnStock() {
        Stock stock = stockService.getStockBySymbol("AAPL");
        
        assertNotNull(stock);
        assertEquals("AAPL", stock.getSymbol());
        assertEquals("Apple Inc.", stock.getName());
    }

    @Test
    void getStockBySymbol_WithInvalidSymbol_ShouldReturnNull() {
        Stock stock = stockService.getStockBySymbol("INVALID");
        
        assertNull(stock);
    }

    @Test
    void addStock_ShouldAddNewStock() {
    Stock newStock = new Stock(
        "TSLA",
        "Tesla Inc.",
        BigDecimal.valueOf(250.00),
        BigDecimal.valueOf(245.00),
        null
    );
        
        Stock added = stockService.addStock(newStock);
        
        assertNotNull(added);
        assertEquals("TSLA", added.getSymbol());
        assertNotNull(added.getLastUpdated());
        
        List<Stock> stocks = stockService.getAllStocks();
        assertEquals(4, stocks.size());
    }

    @Test
    void updateStock_WithValidSymbol_ShouldUpdateStock() {
    Stock updatedStock = new Stock(
        "AAPL",
        "Apple Inc. Updated",
        BigDecimal.valueOf(180.00),
        BigDecimal.valueOf(178.50),
        null
    );
        
        Stock result = stockService.updateStock("AAPL", updatedStock);
        
        assertNotNull(result);
        assertEquals("Apple Inc. Updated", result.getName());
        assertEquals(BigDecimal.valueOf(180.00), result.getCurrentPrice());
    }

    @Test
    void updateStock_WithInvalidSymbol_ShouldReturnNull() {
    Stock updatedStock = new Stock(
        "INVALID",
        "Invalid Stock",
        BigDecimal.valueOf(100.00),
        BigDecimal.valueOf(95.00),
        null
    );
        
        Stock result = stockService.updateStock("INVALID", updatedStock);
        
        assertNull(result);
    }

    @Test
    void deleteStock_WithValidSymbol_ShouldReturnTrue() {
        boolean result = stockService.deleteStock("AAPL");
        
        assertTrue(result);
        assertNull(stockService.getStockBySymbol("AAPL"));
        assertEquals(2, stockService.getAllStocks().size());
    }

    @Test
    void deleteStock_WithInvalidSymbol_ShouldReturnFalse() {
        boolean result = stockService.deleteStock("INVALID");
        
        assertFalse(result);
    }
}
