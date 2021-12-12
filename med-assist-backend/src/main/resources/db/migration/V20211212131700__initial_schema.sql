create table user_authorities
(
    id         varchar(255) not null,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    authority  varchar(255) not null,
    user_id    varchar(255) not null,
    primary key (id)
);

create table users
(
    id            varchar(255) not null,
    created_at    timestamp    not null,
    updated_at    timestamp    not null,
    email_address varchar(255) not null,
    enabled       boolean      not null,
    first_name    varchar(255) not null,
    last_name     varchar(255) not null,
    password      varchar(255) not null,
    username      varchar(255) not null,
    primary key (id)
);

alter table if exists users
    drop constraint if exists unique_username;

alter table if exists users
    add constraint unique_username unique (username);

alter table if exists user_authorities
    add constraint user_id_fk foreign key (user_id) references users;

create table patients
(
    id           varchar(255) not null,
    created_at   timestamp    not null,
    updated_at   timestamp    not null,
    first_name   varchar(255),
    last_name    varchar(255),
    birth_date   date         not null,
    phone_number varchar(20)  not null,
    primary key (id)
);
