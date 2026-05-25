import type { BookState } from './collection';

export type CheckoutResponse = {
  checkoutId: number;
  hrid: string;
  email: string;
  division: string;
  serialNumber: string;
  title: string;
  state: BookState;
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
