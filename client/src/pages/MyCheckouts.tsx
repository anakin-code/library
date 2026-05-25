import { FormEvent, useState } from 'react';
import type { CheckoutResponse, ProblemDetails } from '../models';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function MyCheckouts() {
  const [hrid, setHrid] = useState('A001');
  const [checkouts, setCheckouts] = useState<CheckoutResponse[]>([]);
  const [errorMessage, setErrorMessage] = useState('');
  const [message, setMessage] = useState('');

  const fetchCheckouts = () => {
    setErrorMessage('');
    setMessage('');

    fetch(`${REST_BASE_URL}/api/users/${hrid}/checkouts`)
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          const problem = data as ProblemDetails;
          throw new Error(problem.message);
        }

        setCheckouts(data as CheckoutResponse[]);
      })
      .catch(error => {
        setErrorMessage(error.message);
        setCheckouts([]);
      });
  };

  const submitHandler = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    fetchCheckouts();
  };

  const checkin = (serialNumber: string) => {
    setErrorMessage('');
    setMessage('');

    fetch(`${REST_BASE_URL}/api/checkin`, {
      method: 'PATCH',
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

        setMessage('返却しました');
        fetchCheckouts();
      })
      .catch(error => {
        setErrorMessage(error.message);
      });
  };

  return (
    <section className="page">
      <h1>My Checkouts</h1>

      <form className="input-form" onSubmit={submitHandler}>
        <input
          value={hrid}
          onChange={e => setHrid(e.target.value)}
        />

        <button type="submit">Show</button>
      </form>

      {errorMessage && <p className="error-message">{errorMessage}</p>}
      {message && <p className="success-message">{message}</p>}

      <table>
        <thead>
          <tr>
            <th>Serial</th>
            <th>Title</th>
            <th>State</th>
            <th>Borrowed At</th>
            <th>Due Date</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {checkouts.map(row => (
            <tr key={row.checkoutId}>
              <td>{row.serialNumber}</td>
              <td>{row.title}</td>
              <td>{row.state}</td>
              <td>{row.borrowedAt}</td>
              <td>{row.dueDate}</td>
              <td>
                <button
                  type="button"
                  onClick={() => checkin(row.serialNumber)}
                >
                  Checkin
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
