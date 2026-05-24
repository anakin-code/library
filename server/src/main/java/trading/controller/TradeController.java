package trading.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import trading.controller.dto.TradeInputDto;
import trading.controller.dto.TradeListViewDto;
import trading.services.TradeService;

import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @GetMapping
    public List<TradeListViewDto> listAll() {
        return tradeService.listAllPositions()
                .stream()
                .map(TradeListViewDto::fromDomain)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid TradeInputDto input) {
        tradeService.register(input.toDomain());
    }
}
