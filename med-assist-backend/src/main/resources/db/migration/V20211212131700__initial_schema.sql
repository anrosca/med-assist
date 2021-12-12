create table users
(
    id varchar(255) not null
        constraint users_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    email_address varchar(255) not null,
    enabled boolean not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    password varchar(255) not null,
    username varchar(255) not null
        constraint unique_username
            unique
);

create table user_authorities
(
    id varchar(255) not null
        constraint user_authorities_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    authority varchar(255) not null,
    user_id varchar(255) not null
        constraint user_id_fk
            references users
);

create table patients
(
    id varchar(255) not null
        constraint patients_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    first_name varchar(255),
    last_name varchar(255),
    birth_date date not null,
    phone_number varchar(20) not null
);

create table doctors
(
    specialty varchar(255),
    telephone_number varchar(255),
    id varchar(255) not null
        constraint doctors_pkey
            primary key
        constraint fk_user_id
            references users
);

