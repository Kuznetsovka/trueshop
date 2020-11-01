INSERT INTO products (id, title, price)
values (1, 'Cheese', 450.0);

INSERT INTO products (id, title, price)
values (2, 'Beer', 45.0);

INSERT INTO products (id, title, price)
values (3, 'Milk', 65.0);

INSERT INTO products (id, title, price)
values (4, 'Tomato', 115.0);

INSERT INTO products (id, title, price)
values (5, 'Bread', 58.0);

ALTER SEQUENCE product_seq RESTART WITH 6;

INSERT INTO users (id, archive, email, name, password, role, bucket_id)
values (1, false, 'mail@mail.ru', 'admin', '$2a$10$1M7wbTGC5H8Q12PvFgFFfe49526kJFF1hDoKVOF844GPGW8F9Mtwu', 'ADMIN', null);

ALTER SEQUENCE user_seq RESTART WITH 2;

