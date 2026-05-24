package trading.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import trading.controller.dto.PositionSummaryDto;

import java.util.List;

@Repository
public class PositionRepository {

    private final JdbcTemplate jdbcTemplate;

    public PositionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PositionSummaryDto> findAllPositions() {
        String sql = """
                select
                    s.ticker,
                    s.name,
                    coalesce(sum(
                        case
                            when t.side = 'BUY' then t.quantity
                            when t.side = 'SELL' then -t.quantity
                            else 0
                        end
                    ), 0) as quantity
                from stock s
                left join trade t
                    on s.ticker = t.ticker
                group by
                    s.ticker,
                    s.name
                order by
                    s.ticker asc
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new PositionSummaryDto(
                        rs.getString("ticker"),
                        rs.getString("name"),
                        rs.getLong("quantity")
                )
        );
    }
}
