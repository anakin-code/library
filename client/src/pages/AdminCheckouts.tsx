import { useEffect, useState } from 'react';
import type { AdminCheckoutResponse, ProblemDetails } from '../models';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function AdminCheckouts() {
  const [checkouts, setCheckouts] = useState<AdminCheckoutResponse[]>([]);
  const [errorMessage, setErrorMessage] = useState('');

  const fetchAdminCheckouts = () => {
    setErrorMessage('');

    fetch(`${REST_BASE_URL}/api/admin/checkouts`)
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          const problem = data as ProblemDetails;
          throw new Error(problem.message);
        }

        setCheckouts(data as AdminCheckoutResponse[]);
      })
      .catch(error => {
        setErrorMessage(error.message);
        setCheckouts([]);
      });
  };

  useEffect(() => {
    fetchAdminCheckouts();
  }, []);

  return (
    <section className="page">
      <h1>Admin Checkouts</h1>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <table>
        <thead>
          <tr>
            <th>Serial</th>
            <th>Title</th>
            <th>HRID</th>
            <th>Email</th>
            <th>Division</th>
            <th>Due Date</th>
          </tr>
        </thead>

        <tbody>
          {checkouts.map(row => (
            <tr key={row.serialNumber}>
              <td>{row.serialNumber}</td>
              <td>{row.title}</td>
              <td>{row.hrid}</td>
              <td>{row.email}</td>
              <td>{row.division}</td>
              <td>{row.dueDate}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
