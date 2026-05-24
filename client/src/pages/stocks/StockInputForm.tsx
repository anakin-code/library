import { FormEvent, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import type { Market } from '../../models/stock';
import type { ProblemDetails } from '../../models/problem';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function StockInputForm() {

  const navigate = useNavigate();

  const [ticker, setTicker] = useState('');
  const [name, setName] = useState('');
  const [exchangeMarket, setExchangeMarket]
    = useState<Market>('PRIME');

  const [sharesIssued, setSharesIssued]
    = useState('');

  const [errorMessage, setErrorMessage]
    = useState('');

  const submitHandler = (
    event: FormEvent<HTMLFormElement>
  ) => {

    event.preventDefault();

    setErrorMessage('');

    const parsedSharesIssued = Number(sharesIssued);

    if (!Number.isInteger(parsedSharesIssued) || parsedSharesIssued <= 0) {
      setErrorMessage('Shares Issued は正の整数で入力してください');
      return;
    }

    fetch(`${REST_BASE_URL}/api/stocks`, {

      method: 'POST',

      headers: {
        'Content-Type': 'application/json'
      },

      body: JSON.stringify({
        ticker,
        name,
        exchangeMarket,
        sharesIssued: parsedSharesIssued
      })

    })

      .then(async response => {

        if (response.status === 201) {
          navigate('/stocks');
          return;
        }

        const problem: ProblemDetails =
          await response.json();

        setErrorMessage(
          problem.detail
          ?? `Server error: ${response.status}`
        );

      })

      .catch(error =>
        setErrorMessage(
          `Unexpected error: ${error}`
        )
      );

  };

  return (

    <section className="form-page">

      <h1>
        Register New Stock
      </h1>

      <form
        className="input-form"
        onSubmit={submitHandler}
        noValidate
      >

        <div className="form-row">

          <label htmlFor="ticker">
            Ticker
          </label>

          <input
            id="ticker"
            type="text"
            value={ticker}
            onChange={e => setTicker(e.target.value)}
          />

        </div>

        <div className="form-row">

          <label htmlFor="name">
            Name
          </label>

          <input
            id="name"
            type="text"
            value={name}
            onChange={e => setName(e.target.value)}
          />

        </div>

        <div className="form-row">

          <label htmlFor="exchangeMarket">
            Market
          </label>

          <select
            id="exchangeMarket"
            value={exchangeMarket}
            onChange={e =>
              setExchangeMarket(
                e.target.value as Market
              )
            }
          >

            <option value="PRIME">
              Prime
            </option>

            <option value="STANDARD">
              Standard
            </option>

            <option value="GROWTH">
              Growth
            </option>

          </select>

        </div>

        <div className="form-row">

          <label htmlFor="sharesIssued">
            Shares Issued
          </label>

          <input
            id="sharesIssued"
            type="number"
            value={sharesIssued}
            onChange={e =>
              setSharesIssued(e.target.value)
            }
          />

        </div>

        {errorMessage && (
          <p className="error-message">
            {errorMessage}
          </p>
        )}

        <div className="button-row">

          <button type="submit">
            Register
          </button>

        </div>

      </form>

    </section>

  );

}