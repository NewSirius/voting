DELETE FROM user_roles;
DELETE FROM dishes;
DELETE FROM vote_rating_history;
DELETE FROM vote_user_history;
DELETE FROM restaurants;
DELETE FROM users;
ALTER SEQUENCE users_seq RESTART WITH 1000;
ALTER SEQUENCE dishes_seq RESTART WITH 1000;
ALTER SEQUENCE restaurants_seq RESTART WITH 1000;
ALTER SEQUENCE vote_rating_history_seq RESTART WITH 1000;
ALTER SEQUENCE vote_user_history_seq RESTART WITH 1000;

INSERT INTO users (name, email, password) VALUES
  ('User1', 'user1@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin'),
  ('User2', 'user2@yandex.ru', 'password'),
  ('User3', 'user3@yandex.ru', 'password'),
  ('User4', 'user4@yandex.ru', 'password'),
  ('User5', 'user5@yandex.ru', 'password'),
  ('User6', 'user6@yandex.ru', 'password');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 1000),
  ('ROLE_ADMIN', 1001),
  ('ROLE_USER', 1001),
  ('ROLE_USER', 1002),
  ('ROLE_USER', 1003),
  ('ROLE_USER', 1004),
  ('ROLE_USER', 1005),
  ('ROLE_USER', 1006);

INSERT INTO restaurants (name) VALUES
  ('Restaurant1'),
  ('Restaurant2'),
  ('Restaurant3'),
  ('Restaurant4'),
  ('Restaurant5'),
  ('Restaurant6');

INSERT INTO dishes (restaurant_id, name, price, date) VALUES
  (1000, 'Борщ', 200, current_date),
  (1000, 'Цезарь', 300, current_date),
  (1000, 'Компот', 99, current_date),
  (1001, 'Рыбный суп', 200, current_date),
  (1001, 'Оливье', 190, current_date),
  (1001, 'Чай', 50, current_date),
  (1002, 'Сборная солянка', 250, current_date),
  (1002, 'Макароны', 60, current_date),
  (1002, 'Курица', 70, current_date),
  (1002, 'Сок', 90, current_date),
  (1003, 'Оливье', 190, current_date - 1 DAY),
  (1003, 'Компот', 90, current_date - 1 DAY),
  (1003, 'Цезарь', 350, current_date - 1 DAY),
  (1003, 'Сок', 90, current_date - 1 DAY);

INSERT INTO vote_rating_history (restaurant_id, date, rating) VALUES
  (1000, current_date, 0),
  (1001, current_date, 0),
  (1002, current_date, 3),
  (1003, current_date, 2),
  (1003, current_date - 1 DAY, 1);

INSERT INTO vote_user_history (restaurant_id, date, user_id) VALUES
  (1002, current_date, 1000),
  (1003, current_date - 1 DAY, 1000),
  (1002, current_date, 1002),
  (1002, current_date, 1003),
  (1003, current_date, 1004),
  (1003, current_date, 1005);

