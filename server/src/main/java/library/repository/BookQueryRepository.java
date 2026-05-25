package library.repository;

import library.dto.response.BookCollectionResponse;
import library.dto.response.BookTitleResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class BookQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookQueryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BookTitleResponse> searchBookTitles(
            String keyword,
            String tagName,
            Integer categoryNumber,
            Integer subCategoryNumber
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT
                    bt.id AS book_title_id,
                    bt.title AS title,
                    c.number AS category_number,
                    c.name AS category_name,
                    sc.number AS sub_category_number,
                    sc.name AS sub_category_name,
                    COUNT(DISTINCT bc.id) AS total_copies,
                    COUNT(DISTINCT CASE WHEN bc.state = 'AVAILABLE' THEN bc.id END) AS available_copies,
                    COUNT(DISTINCT CASE WHEN bc.state = 'CHECKED_OUT' THEN bc.id END) AS checked_out_copies,
                    COUNT(DISTINCT CASE WHEN bc.state = 'DEACCESSIONED' THEN bc.id END) AS deaccessioned_copies,
                    COALESCE(GROUP_CONCAT(DISTINCT t.name), '') AS tags
                FROM book_title bt
                JOIN sub_category sc ON sc.id = bt.sub_category_id
                JOIN category c ON c.id = sc.category_id
                LEFT JOIN book_collection bc ON bc.book_title_id = bt.id
                LEFT JOIN book_title_tag btt ON btt.book_title_id = bt.id
                LEFT JOIN tag t ON t.id = btt.tag_id
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND bt.title LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }

        if (tagName != null && !tagName.isBlank()) {
            sql.append("""
                    AND EXISTS (
                        SELECT 1
                        FROM book_title_tag btt2
                        JOIN tag t2 ON t2.id = btt2.tag_id
                        WHERE btt2.book_title_id = bt.id
                          AND t2.name LIKE ?
                    )
                    """);
            params.add("%" + tagName.trim() + "%");
        }

        if (categoryNumber != null) {
            sql.append(" AND c.number = ? ");
            params.add(categoryNumber);
        }

        if (subCategoryNumber != null) {
            sql.append(" AND sc.number = ? ");
            params.add(subCategoryNumber);
        }

        sql.append("""
                GROUP BY bt.id, bt.title, c.number, c.name, sc.number, sc.name
                ORDER BY c.number, sc.number, bt.title
                """);

        return jdbcTemplate.query(
                sql.toString(),
                (rs, rowNum) -> new BookTitleResponse(
                        rs.getLong("book_title_id"),
                        rs.getString("title"),
                        rs.getInt("category_number"),
                        rs.getString("category_name"),
                        rs.getInt("sub_category_number"),
                        rs.getString("sub_category_name"),
                        splitTags(rs.getString("tags")),
                        rs.getLong("total_copies"),
                        rs.getLong("available_copies"),
                        rs.getLong("checked_out_copies"),
                        rs.getLong("deaccessioned_copies")
                ),
                params.toArray()
        );
    }

    public List<BookCollectionResponse> searchBookCollections(
            String keyword,
            String state
    ) {
        StringBuilder sql = new StringBuilder("""
                SELECT
                    bc.id AS collection_id,
                    bt.id AS book_title_id,
                    bc.serial_number AS serial_number,
                    bt.title AS title,
                    bc.state AS state,
                    c.number AS category_number,
                    c.name AS category_name,
                    sc.number AS sub_category_number,
                    sc.name AS sub_category_name
                FROM book_collection bc
                JOIN book_title bt ON bt.id = bc.book_title_id
                JOIN sub_category sc ON sc.id = bt.sub_category_id
                JOIN category c ON c.id = sc.category_id
                WHERE 1 = 1
                """);

        List<Object> params = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            sql.append(" AND bt.title LIKE ? ");
            params.add("%" + keyword.trim() + "%");
        }

        if (state != null && !state.isBlank()) {
            sql.append(" AND bc.state = ? ");
            params.add(state.trim());
        }

        sql.append("""
                ORDER BY c.number, sc.number, bt.title, bc.serial_number
                """);

        return jdbcTemplate.query(
                sql.toString(),
                (rs, rowNum) -> new BookCollectionResponse(
                        rs.getLong("collection_id"),
                        rs.getLong("book_title_id"),
                        rs.getString("serial_number"),
                        rs.getString("title"),
                        rs.getString("state"),
                        rs.getInt("category_number"),
                        rs.getString("category_name"),
                        rs.getInt("sub_category_number"),
                        rs.getString("sub_category_name")
                ),
                params.toArray()
        );
    }

    private List<String> splitTags(String tags) {
        if (tags == null || tags.isBlank()) {
            return List.of();
        }

        return Arrays.stream(tags.split(","))
                .filter(s -> !s.isBlank())
                .sorted()
                .toList();
    }
}