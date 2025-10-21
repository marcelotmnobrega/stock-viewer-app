package com.example.stockviewer.stock;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    
    @PostMapping
    public ResponseEntity<StockDTO> createStock(@Valid @RequestBody StockDTO stockDTO) {
        logger.info("POST /api/stocks - Not supported");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
    
    @PutMapping("/{symbol}")
    public ResponseEntity<StockDTO> updateStock(
            @PathVariable String symbol,
            @Valid @RequestBody StockDTO stockDTO) {
        logger.info("PUT /api/stocks/{} - Updating stock", symbol);
        logger.info("PUT /api/stocks/{} - Not supported", symbol);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
    
    @DeleteMapping("/{symbol}")
    public ResponseEntity<Void> deleteStock(@PathVariable String symbol) {
        logger.info("DELETE /api/stocks/{} - Deleting stock", symbol);
        logger.info("DELETE /api/stocks/{} - Not supported", symbol);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
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
}
