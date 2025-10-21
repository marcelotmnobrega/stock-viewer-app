package com.example.stockviewer.stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
public class StockController {
    
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    
    private final StockService stockService;
    
    public StockController(StockService stockService) {
        this.stockService = stockService;
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
    
    // Removed create and update endpoints per requirements
    
    // Removed delete endpoint per requirements
    
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
}
