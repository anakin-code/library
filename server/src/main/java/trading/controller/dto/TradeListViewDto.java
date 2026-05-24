package trading.controller.dto;

import trading.model.Position;
import trading.model.Side;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TradeListViewDto(
        LocalDateTime tradedDatetime,
        String ticker,
        String name,
        Side side,
        Long quantity,
        BigDecimal tradedPrice
) {
    public static TradeListViewDto fromDomain(Position position) {
        return new TradeListViewDto(
                position.getTradedDatetime(),
                position.getTicker(),
                position.getName(),
                position.getSide(),
                position.getQuantity(),
                position.getTradedPrice()
        );
    }
}
