package library.dto.response;

public record BookCollectionResponse(
        Long collectionId,
        Long bookTitleId,
        String serialNumber,
        String title,
        String state,
        Integer categoryNumber,
        String categoryName,
        Integer subCategoryNumber,
        String subCategoryName
) {}
