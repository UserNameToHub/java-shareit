-- DROP TABLE IF EXISTS users;
-- DROP TABLE IF EXISTS items;
-- DROP TABLE IF EXISTS requests;
-- DROP TABLE IF EXISTS bookings;
-- DROP TABLE IF EXISTS comments;

create table if not exists USERS
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name  VARCHAR(512) NOT NULL
);

create table if not exists items
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    owner_id     BIGINT REFERENCES users (id),
    title        VARCHAR NOT NULL,
    description  VARCHAR(200),
    is_available BOOLEAN DEFAULT FALSE,
    request_id   BIGINT REFERENCES requests (id)
);

create table if not exists requests
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id     BIGINT REFERENCES users (id),
    description VARCHAR(200),
    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL
);

create table if not exists bookings
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    item_id    BIGINT REFERENCES items (id) NOT NULL,
    user_id    BIGINT REFERENCES users (id) NOT NULL,
    status     VARCHAR(20)                  NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
    end_date   TIMESTAMP WITHOUT TIME ZONE  NOT NULL
);

create table if not exists comments
(
    id        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    text      VARCHAR(500) NOT NULL,
    item_id   BIGINT REFERENCES items (id) NOT NULL,
    author_id BIGINT REFERENCES users (id) NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE  NOT NULL
)