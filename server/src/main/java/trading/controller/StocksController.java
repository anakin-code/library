package trading.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import trading.controller.dto.StockInputDto;
import trading.controller.dto.StockListViewDto;
import trading.services.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StocksController {

    private final StockService stockService;

    public StocksController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<StockListViewDto> listAll() {
        return stockService.listAllStocks()
                .stream()
                .map(StockListViewDto::fromDomain)
                .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid StockInputDto input) {
        stockService.register(input.toDomain());
    }
}
