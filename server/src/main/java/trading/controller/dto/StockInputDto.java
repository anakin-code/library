package trading.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import trading.model.Market;
import trading.model.Stock;

public class StockInputDto {

    @NotNull
    @Pattern(regexp = "[0-9]{4}")
    private String ticker;

    @NotNull
    private String name;

    @NotNull
    private Market exchangeMarket;

    @NotNull
    @Min(value = 1)
    private Long sharesIssued;

    public StockInputDto() {}

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Market getExchangeMarket() { return exchangeMarket; }
    public void setExchangeMarket(Market exchangeMarket) { this.exchangeMarket = exchangeMarket; }

    public Long getSharesIssued() { return sharesIssued; }
    public void setSharesIssued(Long sharesIssued) { this.sharesIssued = sharesIssued; }

    public Stock toDomain() {
        return new Stock(this.ticker, this.name, this.exchangeMarket, this.sharesIssued);
    }
}
