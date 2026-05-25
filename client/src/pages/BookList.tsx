import { FormEvent, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import type { BookTitleResponse } from '../models';

const REST_BASE_URL = import.meta.env.VITE_REST_BASE_URL;

export default function BookList() {
  const navigate = useNavigate();

  const [books, setBooks] = useState<BookTitleResponse[]>([]);
  const [keyword, setKeyword] = useState('');
  const [tagName, setTagName] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const fetchBooks = () => {
    setErrorMessage('');

    const params = new URLSearchParams();

    if (keyword) {
      params.append('keyword', keyword);
    }

    if (tagName) {
      params.append('tagName', tagName);
    }

    fetch(`${REST_BASE_URL}/api/books?${params.toString()}`)
      .then(async response => {
        const data = await response.json();

        if (!response.ok) {
          throw new Error(data.message ?? '書籍タイトル一覧の取得に失敗しました');
        }

        setBooks(data as BookTitleResponse[]);
      })
      .catch(error => {
        setErrorMessage(error.message);
        setBooks([]);
      });
  };

  useEffect(() => {
    fetchBooks();
  }, []);

  const submitHandler = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    fetchBooks();
  };

  return (
    <section className="page">
      <h1>Book Title List</h1>

      <form className="input-form" onSubmit={submitHandler}>
        <input
          placeholder="keyword"
          value={keyword}
          onChange={e => setKeyword(e.target.value)}
        />

        <input
          placeholder="tag"
          value={tagName}
          onChange={e => setTagName(e.target.value)}
        />

        <button type="submit">Search</button>
      </form>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Category</th>
            <th>Sub Category</th>
            <th>Tags</th>
            <th>Total</th>
            <th>Available</th>
            <th>Checked Out</th>
            <th>Deaccessioned</th>
            <th>Action</th>
          </tr>
        </thead>

        <tbody>
          {books.map(book => (
            <tr key={book.bookTitleId}>
              <td>{book.title}</td>
              <td>{book.categoryNumber} {book.categoryName}</td>
              <td>{book.subCategoryNumber} {book.subCategoryName}</td>
              <td>{book.tags.join(', ')}</td>
              <td className="number">{book.totalCopies}</td>
              <td className="number">{book.availableCopies}</td>
              <td className="number">{book.checkedOutCopies}</td>
              <td className="number">{book.deaccessionedCopies}</td>
              <td>
                <button
                  type="button"
                  onClick={() =>
                    navigate(`/collections?keyword=${encodeURIComponent(book.title)}`)
                  }
                >
                  View Collections
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </section>
  );
}
