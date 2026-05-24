package trading.model;

public class Stock {

    private String ticker;
    private String name;
    private Market exchangeMarket;
    private Long sharesIssued;

    public Stock(String ticker, String name, Market exchangeMarket, Long sharesIssued) {
        this.ticker = ticker;
        this.name = name;
        this.exchangeMarket = exchangeMarket;
        this.sharesIssued = sharesIssued;
    }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Market getExchangeMarket() { return exchangeMarket; }
    public void setExchangeMarket(Market exchangeMarket) { this.exchangeMarket = exchangeMarket; }

    public Long getSharesIssued() { return sharesIssued; }
    public void setSharesIssued(Long sharesIssued) { this.sharesIssued = sharesIssued; }
}
