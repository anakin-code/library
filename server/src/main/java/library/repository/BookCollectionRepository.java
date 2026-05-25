package library.repository;

import library.entity.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {
    Optional<BookCollection> findBySerialNumber(String serialNumber);
}
