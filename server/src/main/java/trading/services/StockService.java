package trading.services;

import org.springframework.stereotype.Service;
import trading.Error.TickerAlreadyTakenException;
import trading.model.Stock;
import trading.repository.StockRepository;

import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> listAllStocks() {
        return stockRepository.findAll();
    }

    public void register(Stock stock) {
        if (stockRepository.findByTicker(stock.getTicker()).isPresent()) {
            throw new TickerAlreadyTakenException(null);
        }

        stockRepository.insert(stock);
    }
}
