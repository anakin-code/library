import { Link } from 'react-router-dom';

export default function Menu() {
  return (
    <section className="menu-page">
      <h1>Menu</h1>

      <div className="menu-links">

        <Link to="/stocks/new" className="menu-card">
          Register New Stock
        </Link>

        <Link to="/stocks" className="menu-card">
          Stock List
        </Link>

        <Link to="/trade" className="menu-card">
          Trade List
        </Link>

        <Link to="/positions" className="menu-card">
          Position List
        </Link>

      </div>
    </section>
  );
}