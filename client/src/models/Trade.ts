export type Side =
  | 'BUY'
  | 'SELL';

export interface Trade {

  tradedDatetime: string;

  ticker: string;

  name: string;

  side: Side;

  quantity: number;

  tradedPrice: number;

}