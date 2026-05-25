export type BookState =
  | 'AVAILABLE'
  | 'CHECKED_OUT'
  | 'DEACCESSIONED';

export type BookCollectionResponse = {
  collectionId: number;
  bookTitleId: number;
  serialNumber: string;
  title: string;
  state: BookState;
  categoryNumber: number;
  categoryName: string;
  subCategoryNumber: number;
  subCategoryName: string;
};
