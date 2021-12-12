create table doctors
(
    id         varchar(255) not null,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    name       varchar(255),
    speciality varchar(255),
    primary key (id)
);
