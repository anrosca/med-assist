truncate table file_records cascade;
truncate table patients cascade;

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at, is_deleted)
values ('8f735e81-020f-4724-94ed-6b43d8a21582', 'Jim', 'Morrison', '1994-12-13', '+37369952147',
        '2021-12-12 22:10:28.933858', '2021-12-12 22:10:28.933858', false);

insert into file_records (id, created_at, updated_at, data, name, type, patient_id)
values ('3f5f4af2-8738-43be-bed3-7362ab973aec', '2022-01-09 12:07:02.243200', '2022-01-09 12:07:02.243200',
        lo_create(0), 'test.png', 'image/png',
        '8f735e81-020f-4724-94ed-6b43d8a21582');

SELECT pg_catalog.lo_open((select data from file_records where id = '3f5f4af2-8738-43be-bed3-7362ab973aec'), 131072);
SELECT pg_catalog.lowrite(0, '\x74657374');
SELECT pg_catalog.lo_close(0);
