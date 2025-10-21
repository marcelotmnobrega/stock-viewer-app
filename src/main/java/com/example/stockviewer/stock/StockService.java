package com.example.stockviewer.stock;

import com.example.stockviewer.brapi.BrApiPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    private final BrApiPort brApi;

    public StockService(BrApiPort brApi) {
        this.brApi = brApi;
    }

    public Stock getStockBySymbol(String symbol) {
        logger.info("Fetching stock {} from BrApi", symbol);
        return brApi.getQuote(symbol);
    }

    public boolean deleteStock(String symbol) {
        // Not supported in external API scenario
        logger.warn("Delete stock not supported when using external API");
        return false;
    }
}
