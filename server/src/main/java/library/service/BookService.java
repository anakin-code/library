package library.service;

import library.dto.response.BookCollectionResponse;
import library.dto.response.BookTitleResponse;
import library.entity.BookCollection;
import library.repository.BookCollectionRepository;
import library.repository.BookQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {
    private final BookQueryRepository bookQueryRepository;
    private final BookCollectionRepository bookCollectionRepository;

    public BookService(
            BookQueryRepository bookQueryRepository,
            BookCollectionRepository bookCollectionRepository
    ) {
        this.bookQueryRepository = bookQueryRepository;
        this.bookCollectionRepository = bookCollectionRepository;
    }

    @Transactional(readOnly = true)
    public List<BookTitleResponse> searchBookTitles(
            String keyword,
            String tagName,
            Integer categoryNumber,
            Integer subCategoryNumber
    ) {
        return bookQueryRepository.searchBookTitles(keyword, tagName, categoryNumber, subCategoryNumber);
    }

    @Transactional(readOnly = true)
    public List<BookCollectionResponse> searchBookCollections(String keyword, String state) {
        return bookQueryRepository.searchBookCollections(keyword, state);
    }

    @Transactional
    public BookCollectionResponse deaccession(String serialNumber) {
        BookCollection book = bookCollectionRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("指定されたシリアルナンバーの蔵書が存在しません"));

        book.deaccession();

        return toResponse(book);
    }

    private BookCollectionResponse toResponse(BookCollection book) {
        return new BookCollectionResponse(
                book.getId(),
                book.getBookTitle().getId(),
                book.getSerialNumber(),
                book.getBookTitle().getTitle(),
                book.getState().name(),
                book.getBookTitle().getSubCategory().getCategory().getNumber(),
                book.getBookTitle().getSubCategory().getCategory().getName(),
                book.getBookTitle().getSubCategory().getNumber(),
                book.getBookTitle().getSubCategory().getName()
        );
    }
}
