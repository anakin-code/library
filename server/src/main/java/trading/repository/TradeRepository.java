package trading.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trading.model.Position;
import trading.model.Side;

import java.util.List;
import java.util.Optional;

@Repository
public class TradeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TradeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Position> findAll() {
        return jdbcTemplate.query(
                """
                select
                    t.trade_datetime,
                    s.ticker,
                    s.name,
                    t.side,
                    t.quantity,
                    t.traded_price
                from trade t
                inner join stock s
                    on s.ticker = t.ticker
                order by t.trade_datetime desc
                """,
                (rs, rowNum) -> new Position(
                        rs.getTimestamp("trade_datetime").toLocalDateTime(),
                        rs.getString("ticker"),
                        rs.getString("name"),
                        Side.valueOf(rs.getString("side")),
                        rs.getLong("quantity"),
                        rs.getBigDecimal("traded_price")
                )
        );
    }

    public void insert(Position position) {
        jdbcTemplate.update(
                """
                insert into trade(trade_datetime, ticker, side, quantity, traded_price)
                values (?, ?, ?, ?, ?)
                """,
                position.getTradedDatetime(),
                position.getTicker(),
                position.getSide().name(),
                position.getQuantity(),
                position.getTradedPrice()
        );
    }

    public Optional<StockExInfo> findStockExInfoByTicker(String ticker) {
        try {
            StockExInfo stockExInfo = jdbcTemplate.queryForObject(
                    """
                    select name, shares_issued
                    from stock
                    where ticker = ?
                    """,
                    (rs, rowNum) -> new StockExInfo(
                            rs.getString("name"),
                            rs.getLong("shares_issued")
                    ),
                    ticker
            );
            return Optional.of(stockExInfo);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public long calculateCurrentQuantity(String ticker) {
        String sql = """
            select coalesce(sum(
                case
                    when side = 'BUY' then quantity
                    when side = 'SELL' then -quantity
                    else 0
                end
            ), 0)
            from trade
            where ticker = ?
            """;

        Long quantity = jdbcTemplate.queryForObject(sql, Long.class, ticker);
        return quantity == null ? 0L : quantity;
    }


}
