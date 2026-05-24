drop table if exists trade cascade;
drop table if exists stock cascade;

create table stock (
                       ticker varchar(4) primary key,
                       name varchar(255) not null,
                       exchange_market varchar(30) not null,
                       shares_issued bigint not null
);

create table trade (
                       id bigserial primary key,
                       trade_datetime timestamp not null,
                       ticker varchar(4) not null references stock(ticker),
                       side varchar(10) not null,
                       quantity bigint not null,
                       traded_price numeric(15, 2) not null
);

insert into stock(ticker, name, exchange_market, shares_issued) values
('7203', 'Toyota Motor', 'PRIME', 16000000000),
('6758', 'Sony Group', 'PRIME', 1200000000),
('9984', 'SoftBank Group', 'PRIME', 1400000000)
on conflict (ticker) do nothing;

insert into trade(trade_datetime, ticker, side, quantity, traded_price) values
('2026-05-20 10:00:00', '7203', 'BUY', 100, 3000.00),
('2026-05-20 11:00:00', '6758', 'SELL', 200, 12500.00)
on conflict do nothing;
