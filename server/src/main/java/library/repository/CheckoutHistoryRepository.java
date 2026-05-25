package library.repository;

import library.entity.CheckoutHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CheckoutHistoryRepository extends JpaRepository<CheckoutHistory, Long> {
    long countByUserIdAndCheckedInAtIsNull(Long userId);

    @Query("""
            SELECT COUNT(ch) > 0
            FROM CheckoutHistory ch
            WHERE ch.user.id = :userId
              AND ch.bookCollection.bookTitle.id = :bookTitleId
              AND ch.checkedInAt IS NULL
            """)
    boolean existsActiveSameTitle(@Param("userId") Long userId, @Param("bookTitleId") Long bookTitleId);

    Optional<CheckoutHistory> findByBookCollectionIdAndCheckedInAtIsNull(Long bookCollectionId);

    List<CheckoutHistory> findByUserHridAndCheckedInAtIsNullOrderByDueDateAsc(String hrid);

    List<CheckoutHistory> findByUserHridOrderByBorrowedAtDesc(String hrid);

    List<CheckoutHistory> findByCheckedInAtIsNullOrderByDueDateAsc();
}
