DROP TABLE IF EXISTS checkout_history;
DROP TABLE IF EXISTS book_title_tag;
DROP TABLE IF EXISTS book_collection;
DROP TABLE IF EXISTS book_title;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS sub_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS app_user;

CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    hrid VARCHAR(30) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    division VARCHAR(100) NOT NULL,
    is_admin BOOLEAN NOT NULL
);

CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    number INT NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE sub_category (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL REFERENCES category(id),
    number INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    UNIQUE(category_id, number)
);

CREATE TABLE book_title (
    id BIGSERIAL PRIMARY KEY,
    sub_category_id BIGINT NOT NULL REFERENCES sub_category(id),
    title VARCHAR(255) NOT NULL
);

CREATE TABLE book_collection (
    id BIGSERIAL PRIMARY KEY,
    book_title_id BIGINT NOT NULL REFERENCES book_title(id),
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    state VARCHAR(30) NOT NULL
);

CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE book_title_tag (
    id BIGSERIAL PRIMARY KEY,
    book_title_id BIGINT NOT NULL REFERENCES book_title(id),
    tag_id BIGINT NOT NULL REFERENCES tag(id),
    UNIQUE(book_title_id, tag_id)
);

CREATE TABLE checkout_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES app_user(id),
    book_collection_id BIGINT NOT NULL REFERENCES book_collection(id),
    borrowed_at TIMESTAMP NOT NULL,
    due_date DATE NOT NULL,
    checked_in_at TIMESTAMP NULL
);
