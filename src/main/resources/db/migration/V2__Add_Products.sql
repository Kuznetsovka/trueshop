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
SET SQL_SAFE_UPDATES=0;
UPDATE product_seq SET next_val=6 where next_val=1;
SET SQL_SAFE_UPDATES=1;