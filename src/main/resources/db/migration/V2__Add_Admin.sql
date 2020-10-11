INSERT INTO users (id, archive, email, name, password, role, bucket_id)
values (1, false, 'mail@mail.ru', 'user', 'pass', 'ADMIN', null);
SET SQL_SAFE_UPDATES=0;
UPDATE user_seq SET next_val=2 where next_val=1;
SET SQL_SAFE_UPDATES=1;
