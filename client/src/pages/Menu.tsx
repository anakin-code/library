import { Link } from 'react-router-dom';

export default function Menu() {
  return (
    <section className="page">
      <h1>Library Menu</h1>

      <div className="menu-links">
        <Link to="/books" className="menu-card">Book Title List</Link>
        <Link to="/collections" className="menu-card">Book Collection List</Link>
        <Link to="/checkout" className="menu-card">Checkout / Checkin</Link>
        <Link to="/my-checkouts" className="menu-card">My Checkouts</Link>
        <Link to="/histories" className="menu-card">Checkout History</Link>
        <Link to="/admin/checkouts" className="menu-card">Admin Checkouts</Link>
      </div>
    </section>
  );
}
