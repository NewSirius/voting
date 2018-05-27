DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS dishes;
DROP TABLE IF EXISTS vote_rating_history;
DROP TABLE IF EXISTS vote_user_history;
DROP TABLE IF EXISTS restaurants;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS dishes_seq;
DROP SEQUENCE IF EXISTS restaurants_seq;
DROP SEQUENCE IF EXISTS vote_rating_history_seq;
DROP SEQUENCE IF EXISTS vote_user_history_seq;

CREATE SEQUENCE users_seq
  AS INTEGER
  START WITH 1000;
CREATE SEQUENCE dishes_seq
  AS INTEGER
  START WITH 1000;
CREATE SEQUENCE restaurants_seq
  AS INTEGER
  START WITH 1000;
CREATE SEQUENCE vote_rating_history_seq
  AS INTEGER
  START WITH 1000;
CREATE SEQUENCE vote_user_history_seq
  AS INTEGER
  START WITH 1000;

CREATE TABLE users
(
  id         INTEGER GENERATED BY DEFAULT AS SEQUENCE users_seq PRIMARY KEY,
  name       VARCHAR(255)               NOT NULL,
  password   VARCHAR(255)               NOT NULL,
  email      VARCHAR(255)               NOT NULL,
  registered TIMESTAMP DEFAULT now()    NOT NULL,
  enabled    BOOLEAN DEFAULT TRUE       NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
  ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);

CREATE TABLE restaurants
(
  id   INTEGER GENERATED BY DEFAULT AS SEQUENCE restaurants_seq PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

CREATE TABLE dishes (
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE dishes_seq PRIMARY KEY,
  restaurant_id INTEGER              NOT NULL,
  name          VARCHAR(255)         NOT NULL,
  price         NUMERIC              NOT NULL,
  date          DATE DEFAULT now     NOT NULL,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
    ON DELETE CASCADE
);

CREATE TABLE vote_rating_history
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE vote_rating_history_seq PRIMARY KEY,
  restaurant_id INTEGER NOT NULL,
  date          DATE    DEFAULT current_date,
  rating        INTEGER DEFAULT 0,
  CONSTRAINT restaurant_date_idx UNIQUE (restaurant_id, date),
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
    ON DELETE CASCADE
);

CREATE TABLE vote_user_history
(
  id            INTEGER GENERATED BY DEFAULT AS SEQUENCE vote_user_history_seq PRIMARY KEY,
  restaurant_id INTEGER NOT NULL,
  user_id       INTEGER NOT NULL,
  date          TIMESTAMP DEFAULT now(),
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id)
    ON DELETE CASCADE,
  FOREIGN KEY (user_id) REFERENCES users (id)
    ON DELETE CASCADE
);