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

export type BookCollectionResponse = {
  collectionId: number;
  bookTitleId: number;
  serialNumber: string;
  title: string;
  state: string;
  categoryNumber: number;
  categoryName: string;
  subCategoryNumber: number;
  subCategoryName: string;
};

export type CheckoutResponse = {
  checkoutId: number;
  hrid: string;
  email: string;
  division: string;
  serialNumber: string;
  title: string;
  state: string;
  borrowedAt: string;
  dueDate: string;
  checkedInAt: string | null;
};

export type AdminCheckoutResponse = {
  serialNumber: string;
  title: string;
  hrid: string;
  email: string;
  division: string;
  dueDate: string;
};

export type ProblemDetails = {
  timestamp: string;
  status: number;
  error: string;
  message: string;
};
