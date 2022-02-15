--Clean up
truncate treatment_plans cascade;
truncate treatment_tooth cascade ;
truncate teeth cascade;
truncate treatments cascade ;
delete from appointments;
delete from patients;
truncate users cascade;

insert into users (id, created_at, updated_at, email_address, enabled, first_name, last_name, password, username)
values ('620e11c0-7d59-45be-85cc-0dc146532e78', '2021-12-12 17:33:20.998582', '2021-12-12 17:33:20.998582',
        'sponge-bob@gmail.com', true, 'Sponge', 'Bob', '$2a$10$UXU/S0KKSNxGs1AKJcmr0eYQp3FNC1HvWPznfUMFE57qrJaKIp3CC',
        'square-pants-1');
INSERT INTO users (id, created_at, updated_at, email_address, enabled, first_name, last_name, password, username)
VALUES ('f23e4567-e89b-12d3-a456-426614174000', '2021-12-12 22:10:28.931873', '2021-12-12 22:10:28.931873',
        'vusaci@gmail.com', true, 'Vasile', 'Usaci', '$2a$10$GTAQ9YOgXaK1XgePCq998OqGU8UTWk5SNAVkuHXcOstE7YAUOijVi',
        'vusaci');

insert into user_authorities (id, created_at, updated_at, authority, user_id)
values ('454a9641-77a8-4fc5-9858-cc5b71deaa82', '2021-12-12 17:33:21.011111',
        '2021-12-12 17:33:21.011111', 'POWER_USER', '620e11c0-7d59-45be-85cc-0dc146532e78');
INSERT INTO user_authorities (id, created_at, updated_at, authority, user_id)
VALUES ('45c0eceb-e41e-4eaf-82f2-e411f26d33c6', '2021-12-12 22:10:28.933858', '2021-12-12 22:10:28.933858', 'DOCTOR',
        'f23e4567-e89b-12d3-a456-426614174000');

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at)
values ('f44e4567-ef9c-12d3-a45b-52661417400a', 'Jim', 'Morrison', '1994-12-13', '+37369952147', now(), now());

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at)
values ('fc4ec567-ec9c-C2d3-c45b-c26c141c40cc', 'Ray', 'Charles', '1994-12-12', '+37369952149', now(), now());

INSERT INTO doctors (specialty, telephone_number, id)
VALUES ('ORTHODONTIST', '37369666666', 'f23e4567-e89b-12d3-a456-426614174000');

INSERT INTO doctors (specialty, telephone_number, id)
VALUES ('ORTHODONTIST', '37369666667', '620e11c0-7d59-45be-85cc-0dc146532e78');

insert into appointments(id, operation, doctor_id, patient_id, appointment_date, start_time, end_time, created_at, updated_at, primary_color, secondary_color)
values ('ca3e4567-e89b-12d3-b457-5267141750aa', 'Выдача каппы', '620e11c0-7d59-45be-85cc-0dc146532e78',
        'fc4ec567-ec9c-C2d3-c45b-c26c141c40cc', '2021-12-12', '10:00', '18:00', '2021-12-12 17:33:20.998582', '2021-12-12 17:33:20.998582', '#ff1f1f', '#D1E8FF');

insert into appointments(id, operation, doctor_id, patient_id, appointment_date, start_time, end_time, created_at, updated_at, primary_color, secondary_color)
values ('aa3e4567-e89b-12d3-b457-5267141750aa', 'Выдача каппы', 'f23e4567-e89b-12d3-a456-426614174000',
        'f44e4567-ef9c-12d3-a45b-52661417400a', '2021-12-12', '17:00', '18:00', '2021-12-12 17:33:20.998582', '2021-12-12 17:33:20.998582', '#ff1f1f', '#D1E8FF');

insert into appointments(id, operation, doctor_id, patient_id, appointment_date, start_time, end_time, created_at, updated_at, primary_color, secondary_color)
values ('ba3e4567-e89b-12d3-b457-5267141750ab', 'Cleaning', 'f23e4567-e89b-12d3-a456-426614174000',
        'f44e4567-ef9c-12d3-a45b-52661417400a', '2021-12-12', '16:00', '17:00', '2021-12-12 16:00:20.118581', '2021-12-12 16:00:20.118581', '#ff1f1f', '#D1E8FF');
