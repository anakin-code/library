package trading.controller.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import trading.model.Position;
import trading.model.Side;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TradeInputDto {

    @NotNull
    private LocalDateTime tradedDatetime;

    @NotNull
    @Pattern(regexp = "[0-9]{4}")
    private String ticker;

    @NotNull
    private String side;

    @NotNull
    @Min(value = 1)
    private Long quantity;

    @NotNull
    @DecimalMin(value = "0.01")
    @DecimalMax(value = "999999999.99")
    private BigDecimal tradedPrice;

    public TradeInputDto() {}

    public LocalDateTime getTradedDatetime() { return tradedDatetime; }
    public void setTradedDatetime(LocalDateTime tradedDatetime) { this.tradedDatetime = tradedDatetime; }

    public String getTicker() { return ticker; }
    public void setTicker(String ticker) { this.ticker = ticker; }

    public Side getSide() { return Side.getSide(side); }
    public void setSide(String side) { this.side = side; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public BigDecimal getTradedPrice() { return tradedPrice; }
    public void setTradedPrice(BigDecimal tradedPrice) { this.tradedPrice = tradedPrice; }

    public Position toDomain() {
        return new Position(this.tradedDatetime, this.ticker, null, this.getSide(), this.quantity, this.tradedPrice);
    }
}
