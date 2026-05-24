import React from 'react'; //JSXを書くために必要 <AppLayout />HTMLっぽいやつ。
import ReactDOM from 'react-dom/client'; //ブラウザ画面へ描画
import AppLayout from './pages/AppLayout'; // 最初に表示する画面をしてい
import './style.css';  //全画面共通CSS

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode><AppLayout /></React.StrictMode>
);
