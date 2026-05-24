package trading.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Position {

    private LocalDateTime tradedDatetime;
    private String ticker;
    private String name;
    private Side side;
    private Long quantity;
    private BigDecimal tradedPrice;

    public Position(LocalDateTime tradedDatetime, String ticker, String name, Side side, Long quantity, BigDecimal tradedPrice) {
        this.tradedDatetime = tradedDatetime;
        this.ticker = ticker;
        this.name = name;
        this.side = side;
        this.quantity = quantity;
        this.tradedPrice = tradedPrice;
    }

    public LocalDateTime getTradedDatetime() { return tradedDatetime; }
    public void setTradedDatetime(LocalDateTime tradedDatetime) { this.tradedDatetime = tradedDatetime; }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Side getSide() { return side; }
    public void setSide(Side side) { this.side = side; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public BigDecimal getTradedPrice() { return tradedPrice; }
    public void setTradedPrice(BigDecimal tradedPrice) { this.tradedPrice = tradedPrice; }
}
