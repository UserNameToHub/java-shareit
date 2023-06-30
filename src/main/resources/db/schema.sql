create table if not exists items
(
    id          bigint generated by default as identity primary key,
    user_id     bigint references users (id),
    name        varchar not null,
    description varchar(200),
    isAvailable boolean default true,
    request_id  bigint references requests (id)
);

create table if not exists users
(
    id       bigint generated by default as identity primary key,
    email    varchar not null unique,
    login    varchar not null,
    name     varchar,
    birthday timestamp
);

create table if not exists requests
(
    id          bigint generated by default as identity primary key,
    user_id     bigint references users (id),
    description varchar(200),
    created     timestamp not null
)