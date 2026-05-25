import { FormEvent, useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import type {
  BookCollectionResponse,
  BookState,
  ProblemDetails
} from '../models';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function CollectionList() {
  const navigate = useNavigate();
  const location = useLocation();

  const initialKeyword =
    new URLSearchParams(location.search).get('keyword') ?? '';

  const [collections, setCollections] = useState<BookCollectionResponse[]>([]);
  const [keyword, setKeyword] = useState(initialKeyword);
  const [state, setState] = useState<BookState | ''>('');
  const [errorMessage, setErrorMessage] = useState('');

  const fetchCollections = () => {
    setErrorMessage('');

    const params = new URLSearchParams();

    if (keyword) {
      params.append('keyword', keyword);
    }

    if (state) {
      params.append('state', state);
    }

    fetch(`${REST_BASE_URL}/api/collections?${params.toString()}`)
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          const problem = data as ProblemDetails;
          throw new Error(problem.message);
        }

        setCollections(data as BookCollectionResponse[]);
      })
      .catch(error => {
        setErrorMessage(error.message);
        setCollections([]);
      });
  };

  useEffect(() => {
    fetchCollections();
  }, []);

  const submitHandler = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    fetchCollections();
  };

  const deaccession = (serialNumber: string) => {
    setErrorMessage('');

    fetch(`${REST_BASE_URL}/api/collections/${serialNumber}/deaccession`, {
      method: 'PATCH'
    })
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          const problem = data as ProblemDetails;
          throw new Error(problem.message);
        }

        fetchCollections();
      })
      .catch(error => {
        setErrorMessage(error.message);
      });
  };

  return (
    <section className="page">
      <h1>Book Collection List</h1>

      <form className="input-form" onSubmit={submitHandler}>
        <input
          placeholder="keyword"
          value={keyword}
          onChange={e => setKeyword(e.target.value)}
        />

        <select
          value={state}
          onChange={e => setState(e.target.value as BookState | '')}
        >
          <option value="">ALL</option>
          <option value="AVAILABLE">AVAILABLE</option>
          <option value="CHECKED_OUT">CHECKED_OUT</option>
          <option value="DEACCESSIONED">DEACCESSIONED</option>
        </select>

        <button type="submit">Search</button>
      </form>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <table>
        <thead>
          <tr>
            <th>Serial</th>
            <th>Title</th>
            <th>State</th>
            <th>Category</th>
            <th>Sub Category</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {collections.map(row => (
            <tr key={row.collectionId}>
              <td>{row.serialNumber}</td>
              <td>{row.title}</td>
              <td>{row.state}</td>
              <td>{row.categoryNumber} {row.categoryName}</td>
              <td>{row.subCategoryNumber} {row.subCategoryName}</td>
              <td>
                <div className="button-row">
                  <button
                    type="button"
                    disabled={row.state !== 'AVAILABLE'}
                    onClick={() => navigate(`/checkout/${row.serialNumber}`)}
                  >
                    Checkout
                  </button>

                  <button
                    type="button"
                    disabled={row.state !== 'AVAILABLE'}
                    onClick={() => deaccession(row.serialNumber)}
                  >
                    Deaccession
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
