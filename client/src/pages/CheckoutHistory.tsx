import { FormEvent, useState } from 'react';
import type { CheckoutResponse, ProblemDetails } from '../models';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function CheckoutHistory() {
  const [hrid, setHrid] = useState('A001');
  const [histories, setHistories] = useState<CheckoutResponse[]>([]);
  const [errorMessage, setErrorMessage] = useState('');

  const submitHandler = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setErrorMessage('');

    fetch(`${REST_BASE_URL}/api/users/${hrid}/checkout-histories`)
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          const problem = data as ProblemDetails;
          throw new Error(problem.message);
        }

        setHistories(data as CheckoutResponse[]);
      })
      .catch(error => {
        setErrorMessage(error.message);
        setHistories([]);
      });
  };

  return (
    <section className="page">
      <h1>Checkout History</h1>

      <form className="input-form" onSubmit={submitHandler}>
        <input
          value={hrid}
          onChange={e => setHrid(e.target.value)}
        />

        <button type="submit">Show</button>
      </form>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <table>
        <thead>
          <tr>
            <th>Serial</th>
            <th>Title</th>
            <th>State</th>
            <th>Borrowed At</th>
            <th>Due Date</th>
            <th>Checked In At</th>
          </tr>
        </thead>

        <tbody>
          {histories.map(row => (
            <tr key={row.checkoutId}>
              <td>{row.serialNumber}</td>
              <td>{row.title}</td>
              <td>{row.state}</td>
              <td>{row.borrowedAt}</td>
              <td>{row.dueDate}</td>
              <td>{row.checkedInAt ?? '貸出中'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
