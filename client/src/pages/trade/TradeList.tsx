import { useEffect, useState } from 'react';

import type { Trade } from '../../models/Trade';

const REST_BASE_URL =
  import.meta.env.VITE_REST_BASE_URL;

function formatDatetime(value: string) {

  return value
    .replace('T', ' ')
    .slice(0, 16);

}

function formatPrice(value: number) {

  return Number(value).toLocaleString(
    undefined,
    {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2
    }
  );

}

export default function TradeList() {

  const [trades, setTrades]
    = useState<Trade[]>([]);

  const [errorMessage, setErrorMessage]
    = useState('');

  useEffect(() => {

    fetch(`${REST_BASE_URL}/api/trade`)

      .then(async response => {

        if (response.status !== 200) {

          setErrorMessage(
            `Server error: ${response.status}`
          );

          return [];

        }

        return await response.json();

      })

      .then((data: Trade[]) =>
        setTrades(data)
      )

      .catch(error =>
        setErrorMessage(
          `Unexpected error: ${error}`
        )
      );

  }, []);

  return (

    <section className="list-page">

      <h1>
        Trade List
      </h1>

      {errorMessage && (
        <p className="error-message">
          {errorMessage}
        </p>
      )}

      <table className="data-table trade-table">

        <thead>

          <tr>
            <th>Traded Datetime</th>
            <th>Ticker</th>
            <th>Name</th>
            <th>Side</th>

            <th className="number-cell">
              Quantity
            </th>

            <th className="number-cell">
              Traded Price
            </th>
          </tr>

        </thead>

        <tbody>

          {trades.length === 0 ? (

            <tr>
              <td colSpan={6}>
                データなし
              </td>
            </tr>

          ) : (

            trades.map((trade, index) => (

              <tr
                key={
                  `${trade.tradedDatetime}-${trade.ticker}-${index}`
                }
              >

                <td>
                  {formatDatetime(
                    trade.tradedDatetime
                  )}
                </td>

                <td>
                  {trade.ticker}
                </td>

                <td>
                  {trade.name}
                </td>

                <td>
                  {trade.side === 'BUY'
                    ? 'Buy'
                    : 'Sell'}
                </td>

                <td className="number-cell">
                  {trade.quantity.toLocaleString()}
                </td>

                <td className="number-cell">
                  {formatPrice(
                    trade.tradedPrice
                  )}
                </td>

              </tr>

            ))

          )}

        </tbody>

      </table>

    </section>

  );

}