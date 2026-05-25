package library.dto.response;

import java.util.List;

public record BookTitleResponse(
        Long bookTitleId,
        String title,
        Integer categoryNumber,
        String categoryName,
        Integer subCategoryNumber,
        String subCategoryName,
        List<String> tags,
        long totalCopies,
        long availableCopies,
        long checkedOutCopies,
        long deaccessionedCopies
) {}
