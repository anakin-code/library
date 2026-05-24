package trading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import trading.controller.dto.PositionSummaryDto;
import trading.services.PositionService;

import java.util.List;

@RestController
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/api/positions")
    public List<PositionSummaryDto> listAll() {
        return positionService.listAllPositions();
    }
}
