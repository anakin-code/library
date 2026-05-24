package trading.services;

import org.springframework.stereotype.Service;
import trading.Error.BadRequestException;
import trading.model.Position;
import trading.repository.StockExInfo;
import trading.repository.TradeRepository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import trading.model.Side;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Position> listAllPositions() {
        return tradeRepository.findAll();
    }

    public void register(Position position) {
        validate(position);
        tradeRepository.insert(position);
    }

    private void validate(Position position) {
        StockExInfo stockExInfo = tradeRepository
                .findStockExInfoByTicker(position.getTicker())
                .orElseThrow(() -> new BadRequestException("存在しない銘柄コードです"));

        if (position.getSide() == null) {
            throw new BadRequestException("売買区分は BUY または SELL を指定してください");
        }

        long currentQuantity =
                tradeRepository.calculateCurrentQuantity(position.getTicker());

        if (position.getSide() == Side.SELL
                && currentQuantity < position.getQuantity()) {
            throw new BadRequestException("保有数量を超える売却はできません");
        }

        LocalDateTime tradedDatetime = position.getTradedDatetime();

        if (tradedDatetime.isAfter(LocalDateTime.now())) {
            throw new BadRequestException("未来日時の取引は登録できません");
        }

        DayOfWeek dayOfWeek = tradedDatetime.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            throw new BadRequestException("土日の取引は登録できません");
        }

        LocalTime tradeTime = tradedDatetime.toLocalTime();
        if (tradeTime.isBefore(LocalTime.of(9, 0)) || tradeTime.isAfter(LocalTime.of(15, 30))) {
            throw new BadRequestException("取引時間外です");
        }

        if (position.getQuantity() % 100 != 0) {
            throw new BadRequestException("数量は100株単位で入力してください");
        }

        if (position.getQuantity() > stockExInfo.sharesIssued()) {
            throw new BadRequestException("発行済株式数を超える数量は登録できません");
        }
    }
}
