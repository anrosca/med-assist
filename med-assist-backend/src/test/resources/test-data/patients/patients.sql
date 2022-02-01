truncate table patients cascade;

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at, is_deleted)
values ('123e4567-e89b-12d3-a456-426614174000', 'Jim', 'Morrison', '1994-12-13', '+37369952147',
        '2021-12-12 22:10:28.933858', '2021-12-12 22:10:28.933858', false);

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at, is_deleted)
values ('223e4567-e89b-12d3-a456-426614174000', 'Ray', 'Manzarek', '1993-11-13', '+37369952141',
        '2021-11-11 21:10:28.133858', '2021-12-12 22:10:28.933858', true);
