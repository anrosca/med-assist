--Clean up
truncate users cascade;

--Insert test data
insert into users (id, created_at, updated_at, email_address, enabled, first_name, last_name, password, username)
values ('620e11c0-7d59-45be-85cc-0dc146532e78', '2021-12-12 17:33:20.998582', '2021-12-12 17:33:20.998582',
        'sponge-bob@gmail.com', true, 'Sponge', 'Bob', '$2a$10$UXU/S0KKSNxGs1AKJcmr0eYQp3FNC1HvWPznfUMFE57qrJaKIp3CC',
        'square-pants-1');

insert into user_authorities (id, created_at, updated_at, authority, user_id)
values ('454a9641-77a8-4fc5-9858-cc5b71deaa82', '2021-12-12 17:33:21.011111',
        '2021-12-12 17:33:21.011111', 'POWER_USER', '620e11c0-7d59-45be-85cc-0dc146532e78');