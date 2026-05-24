import { useState } from 'react';
import StockInputForm from './StockInputForm';
import StockList from './StockList';

export default function StockIndex() {
  const [reloadKey, setReloadKey] = useState(0);

  return (
    <>
      <StockInputForm onSuccess={() => setReloadKey((current) => current + 1)} />
      <StockList reloadKey={reloadKey} />
    </>
  );
}
