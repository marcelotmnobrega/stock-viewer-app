package com.example.stockviewer.stock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StockService {
    
    private static final Logger logger = LoggerFactory.getLogger(StockService.class);
    
    private final Map<String, Stock> stockCache = new ConcurrentHashMap<>();
    
    public StockService() {
        initializeSampleData();
    }
    
    private void initializeSampleData() {
    addStock(new Stock("AAPL", "Apple Inc.",
        BigDecimal.valueOf(178.50), BigDecimal.valueOf(175.20), LocalDateTime.now()));
        
    addStock(new Stock("GOOGL", "Alphabet Inc.",
        BigDecimal.valueOf(142.30), BigDecimal.valueOf(140.80), LocalDateTime.now()));
        
    addStock(new Stock("MSFT", "Microsoft Corporation",
        BigDecimal.valueOf(378.90), BigDecimal.valueOf(375.10), LocalDateTime.now()));
        
        logger.info("Initialized stock cache with {} stocks", stockCache.size());
    }
    
    public List<Stock> getAllStocks() {
        logger.debug("Fetching all stocks");
        return new ArrayList<>(stockCache.values());
    }
    
    public Stock getStockBySymbol(String symbol) {
        logger.debug("Fetching stock with symbol: {}", symbol);
        Stock stock = stockCache.get(symbol.toUpperCase());
        if (stock == null) {
            logger.warn("Stock not found for symbol: {}", symbol);
        }
        return stock;
    }
    
    public Stock addStock(Stock stock) {
        String symbol = stock.getSymbol().toUpperCase();
        stock.setSymbol(symbol);
        stock.setLastUpdated(LocalDateTime.now());
        stockCache.put(symbol, stock);
        logger.info("Added stock: {}", symbol);
        return stock;
    }
    
    public Stock updateStock(String symbol, Stock updatedStock) {
        String upperSymbol = symbol.toUpperCase();
        Stock existingStock = stockCache.get(upperSymbol);
        
        if (existingStock == null) {
            logger.warn("Cannot update - stock not found: {}", symbol);
            return null;
        }
        
        updatedStock.setSymbol(upperSymbol);
        updatedStock.setLastUpdated(LocalDateTime.now());
        stockCache.put(upperSymbol, updatedStock);
        logger.info("Updated stock: {}", upperSymbol);
        return updatedStock;
    }
    
    public boolean deleteStock(String symbol) {
        String upperSymbol = symbol.toUpperCase();
        Stock removed = stockCache.remove(upperSymbol);
        if (removed != null) {
            logger.info("Deleted stock: {}", upperSymbol);
            return true;
        }
        logger.warn("Cannot delete - stock not found: {}", symbol);
        return false;
    }
}
