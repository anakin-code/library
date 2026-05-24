package trading.controller.dto;

import trading.model.Market;
import trading.model.Stock;

public record StockListViewDto(
        String name,
        String ticker,
        Market exchangeMarket,
        Long sharesIssued
) {
    public static StockListViewDto fromDomain(Stock stock) {
        return new StockListViewDto(
                stock.getName(),
                stock.getTicker(),
                stock.getExchangeMarket(),
                stock.getSharesIssued()
        );
    }
}
