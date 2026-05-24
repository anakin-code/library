import { useEffect, useState } from 'react';
import type { PositionSummary } from '../../models/position';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function PositionList() {
  const [positions, setPositions] = useState<PositionSummary[]>([]);
  const [errorMessage, setErrorMessage] = useState('');

  useEffect(() => {
    fetch(`${REST_BASE_URL}/api/positions`)
      .then(async (response) => {
        if (response.status !== 200) {
          setErrorMessage(`Server error: ${response.status}`);
          return [];
        }

        return await response.json();
      })
      .then((data: PositionSummary[]) => {
        setPositions(data);
      })
      .catch((error) => {
        setErrorMessage(`Unexpected error: ${error}`);
      });
  }, []);

  return (
    <section className="list-page">
      <h1>Position List</h1>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <table className="data-table position-table">
        <thead>
          <tr>
            <th>Ticker</th>
            <th>Name</th>
            <th className="number-cell">Quantity</th>
          </tr>
        </thead>
        <tbody>
          {positions.length === 0 ? (
            <tr>
              <td colSpan={3}>データなし</td>
            </tr>
          ) : (
            positions.map((position) => (
              <tr key={position.ticker}>
                <td>{position.ticker}</td>
                <td>{position.name}</td>
                <td className="number-cell">
                  {position.quantity.toLocaleString()}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </section>
  );
}
