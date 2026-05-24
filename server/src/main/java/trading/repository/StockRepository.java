package trading.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trading.model.Market;
import trading.model.Stock;

import java.util.List;
import java.util.Optional;

@Repository
public class StockRepository {

    private final JdbcTemplate jdbcTemplate;

    public StockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Stock> findAll() {
        return jdbcTemplate.query(
                """
                select ticker, name, exchange_market, shares_issued
                from stock
                order by ticker
                """,
                (rs, rowNum) -> new Stock(
                        rs.getString("ticker"),
                        rs.getString("name"),
                        Market.valueOf(rs.getString("exchange_market")),
                        rs.getLong("shares_issued")
                )
        );
    }

    public int insert(Stock stock) {
        return jdbcTemplate.update(
                """
                insert into stock(ticker, name, exchange_market, shares_issued)
                values (?, ?, ?, ?)
                """,
                stock.getTicker(),
                stock.getName(),
                stock.getExchangeMarket().name(),
                stock.getSharesIssued()
        );
    }

    public Optional<Stock> findByTicker(String ticker) {
        try {
            Stock stock = jdbcTemplate.queryForObject(
                    """
                    select ticker, name, exchange_market, shares_issued
                    from stock
                    where ticker = ?
                    """,
                    (rs, rowNum) -> new Stock(
                            rs.getString("ticker"),
                            rs.getString("name"),
                            Market.valueOf(rs.getString("exchange_market")),
                            rs.getLong("shares_issued")
                    ),
                    ticker
            );
            return Optional.of(stock);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
