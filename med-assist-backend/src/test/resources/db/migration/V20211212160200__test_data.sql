insert into users (id, created_at, updated_at, email_address, enabled, first_name, last_name, password, username)
values ('620e11c0-7d59-45be-85cc-0dc146532e78', '2021-12-12 17:33:20.998582', '2021-12-12 17:33:20.998582',
        'sponge-bob@gmail.com', true, 'Sponge', 'Bob', '$2a$10$UXU/S0KKSNxGs1AKJcmr0eYQp3FNC1HvWPznfUMFE57qrJaKIp3CC',
        'square-pants-1');
INSERT INTO users (id, created_at, updated_at, email_address, enabled, first_name, last_name, password, username)
VALUES ('15297b89-045a-4daa-998f-5995fd44da3e', '2021-12-12 22:10:28.931873', '2021-12-12 22:10:28.931873',
        'vusaci@gmail.com', true, 'Vasile', 'Usaci', '$2a$10$GTAQ9YOgXaK1XgePCq998OqGU8UTWk5SNAVkuHXcOstE7YAUOijVi',
        'vusaci');

insert into user_authorities (id, created_at, updated_at, authority, user_id)
values ('454a9641-77a8-4fc5-9858-cc5b71deaa82', '2021-12-12 17:33:21.011111',
        '2021-12-12 17:33:21.011111', 'POWER_USER', '620e11c0-7d59-45be-85cc-0dc146532e78');
INSERT INTO user_authorities (id, created_at, updated_at, authority, user_id)
VALUES ('45c0eceb-e41e-4eaf-82f2-e411f26d33c6', '2021-12-12 22:10:28.933858', '2021-12-12 22:10:28.933858', 'DOCTOR',
        '15297b89-045a-4daa-998f-5995fd44da3e');

insert into patients(id, first_name, last_name, birth_date, phone_number, created_at, updated_at)
values ('123e4567-e89b-12d3-a456-426614174000', 'Jim', 'Morrison', '1994-12-13', '+37369952147', now(), now());
INSERT INTO doctors (specialty, telephone_number, id)
VALUES ('ORTHODONTIST', '37369666666', '15297b89-045a-4daa-998f-5995fd44da3e');
