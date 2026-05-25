export type BookTitleResponse = {
  bookTitleId: number;
  title: string;
  categoryNumber: number;
  categoryName: string;
  subCategoryNumber: number;
  subCategoryName: string;
  tags: string[];
  totalCopies: number;
  availableCopies: number;
  checkedOutCopies: number;
  deaccessionedCopies: number;
};
