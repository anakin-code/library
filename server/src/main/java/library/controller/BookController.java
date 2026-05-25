package library.controller;

import library.dto.response.BookCollectionResponse;
import library.dto.response.BookTitleResponse;
import library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookTitleResponse> searchBooks(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tagName,
            @RequestParam(required = false) Integer categoryNumber,
            @RequestParam(required = false) Integer subCategoryNumber
    ) {
        return bookService.searchBookTitles(keyword, tagName, categoryNumber, subCategoryNumber);
    }

    @GetMapping("/collections")
    public List<BookCollectionResponse> searchCollections(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String state
    ) {
        return bookService.searchBookCollections(keyword, state);
    }

    @PatchMapping("/collections/{serialNumber}/deaccession")
    public BookCollectionResponse deaccession(
            @PathVariable String serialNumber
    ) {
        return bookService.deaccession(serialNumber);
    }
}
