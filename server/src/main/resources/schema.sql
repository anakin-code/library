drop table if exists trade;
drop table if exists stock;

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
