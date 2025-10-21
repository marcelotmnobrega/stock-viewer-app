package com.example.stockviewer.stock;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    
    private final StockService stockService;
    
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    
    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        logger.info("GET /api/stocks - Fetching all stocks");
    List<StockDTO> stocks = stockService.getAllStocks().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
        return ResponseEntity.ok(stocks);
    }
    
    @GetMapping("/{symbol}")
    public ResponseEntity<StockDTO> getStockBySymbol(@PathVariable String symbol) {
        logger.info("GET /api/stocks/{} - Fetching stock", symbol);
        Stock stock = stockService.getStockBySymbol(symbol);
        
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(convertToDTO(stock));
    }
    
    @PostMapping
    public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
        logger.info("POST /api/stocks - Creating stock: {}", stockDTO.getSymbol());
    Stock stock = convertToEntity(stockDTO);
        Stock created = stockService.addStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(created));
    }
    
    @PutMapping("/{symbol}")
    public ResponseEntity<StockDTO> updateStock(
            @PathVariable String symbol,
            @Valid @RequestBody StockDTO stockDTO) {
        logger.info("PUT /api/stocks/{} - Updating stock", symbol);
        Stock stock = convertToEntity(stockDTO);
        Stock updated = stockService.updateStock(symbol, stock);
        
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(convertToDTO(updated));
    }
    
    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> deleteStock(@PathVariable String symbol) {
        logger.info("DELETE /api/stocks/{} - Deleting stock", symbol);
        boolean deleted = stockService.deleteStock(symbol);
        
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.noContent().build();
    }
    
    private StockDTO convertToDTO(Stock stock) {
    StockDTO dto = new StockDTO();
    dto.setSymbol(stock.getSymbol());
    dto.setName(stock.getName());
    dto.setCurrentPrice(stock.getCurrentPrice());
    dto.setPreviousClose(stock.getPreviousClose());
    dto.setLastUpdated(stock.getLastUpdated());
    dto.setPriceChange(stock.getPriceChange());
    dto.setPriceChangePercentage(stock.getPriceChangePercentage());
    return dto;
    }
    
    private Stock convertToEntity(StockDTO dto) {
    return new Stock(
        dto.getSymbol(),
        dto.getName(),
        dto.getCurrentPrice(),
        dto.getPreviousClose(),
        dto.getLastUpdated()
    );
    }
}
