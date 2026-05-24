import { useState } from 'react';
import TradeInputForm from './TradeInputForm';
import TradeList from './TradeList';

export default function TradeIndex() {
  const [reloadKey, setReloadKey] = useState(0);

  return (
    <>
      <TradeInputForm onSuccess={() => setReloadKey((current) => current + 1)} />
      <TradeList reloadKey={reloadKey} />
    </>
  );
}
