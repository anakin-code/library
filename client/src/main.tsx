import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Route, Routes } from 'react-router-dom';

import Menu from './pages/Menu';
import BookList from './pages/BookList';
import CollectionList from './pages/CollectionList';
import CheckoutForm from './pages/CheckoutForm';
import MyCheckouts from './pages/MyCheckouts';
import CheckoutHistory from './pages/CheckoutHistory';
import AdminCheckouts from './pages/AdminCheckouts';

import './style.css';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Menu />} />
        <Route path="/books" element={<BookList />} />
        <Route path="/collections" element={<CollectionList />} />
        <Route path="/checkout" element={<CheckoutForm />} />
        <Route path="/checkout/:serialNumber" element={<CheckoutForm />} />
        <Route path="/my-checkouts" element={<MyCheckouts />} />
        <Route path="/histories" element={<CheckoutHistory />} />
        <Route path="/admin/checkouts" element={<AdminCheckouts />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
