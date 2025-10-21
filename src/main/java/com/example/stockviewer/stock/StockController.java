package com.example.stockviewer.stock;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock", description = "Brazilian Stock Market Data API")
public class StockController {
    
    private static final Logger logger = LoggerFactory.getLogger(StockController.class);
    
    private final StockService stockService;
    
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }
    
    @Operation(
        summary = "Get stock by symbol",
        description = "Retrieves current stock market data for a given Brazilian stock symbol from BrApi"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Stock data retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StockDTO.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Stock symbol not found",
            content = @Content
        )
    })
    @GetMapping("/{symbol}")
    public ResponseEntity<StockDTO> getStockBySymbol(
            @Parameter(description = "Brazilian stock symbol (e.g., PETR4, VALE3)", required = true)
            @PathVariable String symbol) {
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
