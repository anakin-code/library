import { FormEvent, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import type { ProblemDetails } from '../models';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function CheckoutForm() {
  const { serialNumber: initialSerialNumber = '' } = useParams();

  const [hrid, setHrid] = useState('A001');
  const [serialNumber, setSerialNumber] =
    useState(initialSerialNumber);
  const [message, setMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const request = (url: string, method: string) => {
    setMessage('');
    setErrorMessage('');

    fetch(url, {
      method,
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        hrid,
        serialNumber
      })
    })
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          const problem = data as ProblemDetails;
          throw new Error(problem.message);
        }

        setMessage('処理が完了しました');
      })
      .catch(error => {
        setErrorMessage(error.message);
      });
  };

  const checkoutHandler = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    request(`${REST_BASE_URL}/api/checkout`, 'POST');
  };

  const checkinHandler = () => {
    request(`${REST_BASE_URL}/api/checkin`, 'PATCH');
  };

  return (
    <section className="page">
      <h1>Checkout / Checkin</h1>

      <p>
        <Link to="/collections">
          Back to Collection List
        </Link>
      </p>

      <form
        className="input-form"
        onSubmit={checkoutHandler}
      >
        <label>
          HRID
          <input
            value={hrid}
            onChange={e => setHrid(e.target.value)}
          />
        </label>

        <label>
          Serial Number
          <input
            value={serialNumber}
            onChange={e => setSerialNumber(e.target.value)}
          />
        </label>

        <div className="button-row">
          <button type="submit">Checkout</button>

          <button
            type="button"
            onClick={checkinHandler}
          >
            Checkin
          </button>
        </div>
      </form>

      {errorMessage && <p className="error-message">{errorMessage}</p>}
      {message && <p className="success-message">{message}</p>}
    </section>
  );
}
