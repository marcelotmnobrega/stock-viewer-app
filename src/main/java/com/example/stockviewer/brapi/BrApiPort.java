package com.example.stockviewer.brapi;

import com.example.stockviewer.stock.Stock;

import java.util.List;

public interface BrApiPort {
    List<Stock> getQuotes(List<String> symbols);
    Stock getQuote(String symbol);
}
