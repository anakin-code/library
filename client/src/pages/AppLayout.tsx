import { BrowserRouter, Link, Navigate, Route, Routes } from 'react-router-dom';
import Menu from './Menu';
import StockInputForm from './stocks/StockInputForm';
import StockList from './stocks/StockList';
import TradeInputForm from './trade/TradeInputForm';
import TradeList from './trade/TradeList';
import PositionList from "./positions/PositionList";

export default function AppLayout() {
  return (
    <BrowserRouter>
      <header className="app-header">
        <nav className="nav-menu">
          <Link to="/">Menu</Link>
          <Link to="/stocks/new">Register New Stock</Link>
          <Link to="/stocks">Stock List</Link>
          <Link to="/trade">Trade List</Link>
          <Link to="/positions">Position List</Link>
        </nav>
      </header>
      <main className="page">
        <Routes>
          <Route path="/" element={<Menu />} />
          <Route path="/stocks/new" element={<StockInputForm />} />
          <Route path="/stocks" element={<StockList />} />
          <Route path="/trade/new/:ticker" element={<TradeInputForm />} />
          <Route path="/trade" element={<TradeList />} />
          <Route path="/positions" element={<PositionList />} />
          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}
