package trading.controller.dto;

public record PositionSummaryDto(
        String ticker,
        String name,
        Long quantity
) {
}
