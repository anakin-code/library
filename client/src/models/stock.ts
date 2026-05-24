export type Market =
   'PRIME',
   'STANDARD',
   'GROWTH';

export interface Stock {

  ticker: string;

  name: string;

  exchangeMarket: Market;

  sharesIssued: number;

}