import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

import type { Stock } from '../../models/stock';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function StockList() {

  const [stocks, setStocks] = useState<Stock[]>([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {

    fetch(`${REST_BASE_URL}/api/stocks`)
      .then(async response => {

        if (response.status !== 200) {
          setErrorMessage(`Server error: ${response.status}`);
          return [];
        }

        return await response.json();

      })
      .then((data: Stock[]) => setStocks(data))
      .catch(error =>
        setErrorMessage(`Unexpected error: ${error}`)
      );

  }, []);

  return (
    <section className="list-page">

      <h1>Stock List</h1>

      {errorMessage && (
        <p className="error-message">
          {errorMessage}
        </p>
      )}

      <table className="data-table stock-table">

        <thead>
          <tr>
            <th>Ticker</th>
            <th>Name</th>
            <th>Market</th>
            <th className="number-cell">
              Shares Issued
            </th>
            <th className="center-cell">
              Input Trade
            </th>
          </tr>
        </thead>

        <tbody>

          {stocks.length === 0 ? (

            <tr>
              <td colSpan={5}>
                データなし
              </td>
            </tr>

          ) : (

            stocks.map(stock => (
              <tr key={stock.ticker}>

                <td>
                  {stock.ticker}
                </td>

                <td>
                  {stock.name}
                </td>

                <td>
                  {stock.exchangeMarket}
                </td>

                <td className="number-cell">
                  {stock.sharesIssued.toLocaleString()}
                </td>

                <td className="center-cell">
                  <Link to={`/trade/new/${stock.ticker}`}>
                    Link
                  </Link>
                </td>

              </tr>
            ))

          )}

        </tbody>

      </table>

    </section>
  );
}