import { FormEvent, useState } from 'react';
import {
  useNavigate,
  useParams
} from 'react-router-dom';

import type { ProblemDetails }
  from '../../models/problem';

import type { Side }
  from '../../models/Trade';

const REST_BASE_URL =
  import.meta.env.VITE_REST_BASE_URL;

function toLocalDatetimeValue(
  date: Date
) {

  const pad = (v: number) =>
    String(v).padStart(2, '0');

  return (
    `${date.getFullYear()}-`
    + `${pad(date.getMonth() + 1)}-`
    + `${pad(date.getDate())}T`
    + `${pad(date.getHours())}:`
    + `${pad(date.getMinutes())}`
  );

}

export default function TradeInputForm() {

  const navigate = useNavigate();

  const { ticker = '' } = useParams();

  const [
    tradedDatetime,
    setTradedDatetime
  ] = useState(
    toLocalDatetimeValue(new Date())
  );

  const [side, setSide]
    = useState<Side | ''>('');

  const [quantity, setQuantity]
    = useState('');

  const [tradedPrice, setTradedPrice]
    = useState('');

  const [errorMessage, setErrorMessage]
    = useState('');

  const submitHandler = (
    event: FormEvent<HTMLFormElement>
  ) => {

    event.preventDefault();

    setErrorMessage('');

    fetch(`${REST_BASE_URL}/api/trade`, {

      method: 'POST',

      headers: {
        'Content-Type': 'application/json'
      },

      body: JSON.stringify({
        ticker,
        tradedDatetime,
        side,
        quantity: Number(quantity),
        tradedPrice: Number(tradedPrice)
      })

    })

      .then(async response => {

        if (response.status === 201) {

          navigate('/trade');

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
        Register New Trade
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
            disabled
          />

        </div>

        <div className="form-row">

          <label htmlFor="tradedDatetime">
            Traded Datetime
          </label>

          <input
            id="tradedDatetime"
            type="datetime-local"
            value={tradedDatetime}
            onChange={e =>
              setTradedDatetime(
                e.target.value
              )
            }
          />

        </div>

        <div className="form-row">

          <span>
            Side
          </span>

          <div className="radio-group">

            <label>

              <input
                type="radio"
                name="side"
                value="BUY"
                checked={side === 'BUY'}
                onChange={e =>
                  setSide(
                    e.target.value as Side
                  )
                }
              />

              Buy

            </label>

            <label>

              <input
                type="radio"
                name="side"
                value="SELL"
                checked={side === 'SELL'}
                onChange={e =>
                  setSide(
                    e.target.value as Side
                  )
                }
              />

              Sell

            </label>

          </div>

        </div>

        <div className="form-row">

          <label htmlFor="quantity">
            Quantity
          </label>

          <input
            id="quantity"
            type="number"
            value={quantity}
            onChange={e =>
              setQuantity(
                e.target.value
              )
            }
          />

        </div>

        <div className="form-row">

          <label htmlFor="tradedPrice">
            Traded Price
          </label>

          <input
            id="tradedPrice"
            type="number"
            step="0.01"
            value={tradedPrice}
            onChange={e =>
              setTradedPrice(
                e.target.value
              )
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