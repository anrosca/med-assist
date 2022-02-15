truncate table patients cascade;
truncate table treatment_plans cascade;

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at, is_deleted)
values ('123e4567-e89b-12d3-a456-426614174000', 'Jim', 'Morrison', '1994-12-13', '+37369952147',
        '2021-12-12 22:10:28.933858', '2021-12-12 22:10:28.933858', false);

insert into treatment_plans(id, plan_name, patient_id, created_at, updated_at)
values ('ae3e4567-e89b-e2d3-a4e6-426e14174b0a', 'Install braces', '123e4567-e89b-12d3-a456-426614174000',
        '2021-11-11 21:10:28.133858', '2021-12-12 22:10:28.933858');
insert into treatment_plans(id, plan_name, patient_id, created_at, updated_at)
values ('be3e4567-eb9b-e2d3-b4e6-c26e14174bba', 'Splinting of teeth following trauma',
        '123e4567-e89b-12d3-a456-426614174000', '2021-11-11 21:10:28.133858', '2021-12-12 22:10:28.933858');
