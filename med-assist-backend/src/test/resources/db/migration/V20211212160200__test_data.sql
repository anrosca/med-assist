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

--disabled doctor
INSERT INTO users (id, created_at, updated_at, email_address, enabled, first_name, last_name, password, username)
VALUES ('22297b89-222a-4daa-222f-5995fd44da3e', '2020-12-12 22:10:30.931873', '2021-12-12 22:10:28.931873',
        'john@gmail.com', false, 'John', 'Dorian', '$2a$10$GTAQ9YOgXaK1XgePCq998OqGU8UTWk5SNAVkuHXcOstE7YAUOijVi', 'jd');
insert into user_authorities (id, created_at, updated_at, authority, user_id)
values ('222a9641-88a8-4fc5-8858-cccb71deaacc', '2021-12-12 17:33:21.011111',
        '2020-11-12 11:33:11.011111', 'POWER_USER', '22297b89-222a-4daa-222f-5995fd44da3e');
INSERT INTO doctors (specialty, telephone_number, id)
VALUES ('ORTHODONTIST', '37369661661', '22297b89-222a-4daa-222f-5995fd44da3e');

INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('56a2b905-1e3e-41bf-a115-8c3de1e83760', '2022-01-02 18:57:54.562174', '2022-01-02 18:57:54.562174', false, 'UR1', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('dad1aa00-0de2-4c49-b36b-6e12c7e0c691', '2022-01-02 18:57:54.564174', '2022-01-02 18:57:54.564174', false, 'UR2', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('96aac20d-8809-4756-8e7c-6916b608b15f', '2022-01-02 18:57:54.565174', '2022-01-02 18:57:54.565174', false, 'UR3', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('22c8fa30-d008-48ab-b882-c590e7cd0c56', '2022-01-02 18:57:54.565174', '2022-01-02 18:57:54.565174', false, 'UR4', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('81962c74-7b70-4a40-8593-e721a48e2a62', '2022-01-02 18:57:54.565174', '2022-01-02 18:57:54.565174', false, 'UR5', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('66badb19-88ad-42a4-b3cb-cd0ec34f467f', '2022-01-02 18:57:54.565174', '2022-01-02 18:57:54.565174', false, 'UR6', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('8fed9e4a-f51d-47a7-a502-1bd2ffa08599', '2022-01-02 18:57:54.565174', '2022-01-02 18:57:54.565174', false, 'UR7', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('44553920-07f7-477b-b50c-d8182f9c898c', '2022-01-02 18:57:54.566173', '2022-01-02 18:57:54.566173', false, 'UR8', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('1316f985-d52e-41da-b4f4-81164532c8ee', '2022-01-02 18:57:54.566173', '2022-01-02 18:57:54.566173', false, 'UL1', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('42def3af-5c22-4609-8cbf-4e5de83388ac', '2022-01-02 18:57:54.566173', '2022-01-02 18:57:54.566173', false, 'UL2', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('7e351773-df34-4e99-8cb1-16e4097ac3df', '2022-01-02 18:57:54.566173', '2022-01-02 18:57:54.566173', false, 'UL3', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('496274e9-2aa7-4c17-8095-2b8a2ccf5b84', '2022-01-02 18:57:54.566173', '2022-01-02 18:57:54.566173', false, 'UL4', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('93151595-df19-4b16-8379-d9cba80bf4d5', '2022-01-02 18:57:54.567342', '2022-01-02 18:57:54.567342', false, 'UL5', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('8f1695f2-3569-42b2-8ad8-ec6cad919f31', '2022-01-02 18:57:54.567556', '2022-01-02 18:57:54.567556', false, 'UL6', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('da855742-e68e-4b6e-9c13-887cd99e1dfb', '2022-01-02 18:57:54.567844', '2022-01-02 18:57:54.567844', false, 'UL7', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('fa3d9bd9-6f42-4f50-9c43-349469a75286', '2022-01-02 18:57:54.567844', '2022-01-02 18:57:54.567844', false, 'UL8', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('443f0229-8164-4f47-92c9-5a4e50c66455', '2022-01-02 18:57:54.568172', '2022-01-02 18:57:54.568172', false, 'LL1', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('a9a81272-7cb4-4955-8552-db273c00e11e', '2022-01-02 18:57:54.568172', '2022-01-02 18:57:54.568172', false, 'LL2', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('6c9e4309-ca9a-4c09-9484-dc1fbd38b80c', '2022-01-02 18:57:54.568172', '2022-01-02 18:57:54.568172', false, 'LL3', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('97d06d1c-fbe7-4493-acd4-65caeeec9c5b', '2022-01-02 18:57:54.568172', '2022-01-02 18:57:54.568172', false, 'LL4', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('8b694b1c-4166-41e8-82f2-3c4673d6c887', '2022-01-02 18:57:54.569174', '2022-01-02 18:57:54.569174', false, 'LL5', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('13f1cef1-4abc-4208-987c-0a78f2cc012a', '2022-01-02 18:57:54.569174', '2022-01-02 18:57:54.569174', false, 'LL6', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('9e464251-8736-4959-80c6-f37bb7a1648d', '2022-01-02 18:57:54.569616', '2022-01-02 18:57:54.569616', false, 'LL7', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('9f3929af-afea-4760-91ef-d16d060d9ffb', '2022-01-02 18:57:54.569616', '2022-01-02 18:57:54.569616', false, 'LL8', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('ed65b088-4a74-42b6-a9ae-1d94e46b1684', '2022-01-02 18:57:54.570149', '2022-01-02 18:57:54.570149', false, 'LR1', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('6804428c-cc81-4655-8099-17f3ded276b2', '2022-01-02 18:57:54.570149', '2022-01-02 18:57:54.570149', false, 'LR2', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('1769ae2a-a34d-48e4-ba48-9332a5311399', '2022-01-02 18:57:54.570149', '2022-01-02 18:57:54.570149', false, 'LR3', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('13a99d0e-00ab-488d-8cc0-e8e11f3dd80c', '2022-01-02 18:57:54.570149', '2022-01-02 18:57:54.570149', false, 'LR4', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('66b84f4f-98f7-4b32-a751-0ab2ce9cb13c', '2022-01-02 18:57:54.570149', '2022-01-02 18:57:54.570149', false, 'LR5', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('d391202d-e4a2-4012-8840-039afec18986', '2022-01-02 18:57:54.570149', '2022-01-02 18:57:54.570149', false, 'LR6', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('d0009324-7bd5-4b2e-b044-b2db9bc3880e', '2022-01-02 18:57:54.571141', '2022-01-02 18:57:54.571141', false, 'LR7', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('e29c2861-bdae-4190-a1f3-62a17acee817', '2022-01-02 18:57:54.571141', '2022-01-02 18:57:54.571141', false, 'LR8', '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO treatments (id, created_at, updated_at, description, price, doctor_id, patient_id) VALUES ('ff01f8ab-2ce9-46db-87be-a00142830a05', '2022-01-02 23:04:30.908247', '2022-01-02 23:04:30.908247', 'Veneers', 2000, '15297b89-045a-4daa-998f-5995fd44da3e', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO treatment_tooth (treatment_id, tooth_id) VALUES ('ff01f8ab-2ce9-46db-87be-a00142830a05', 'e29c2861-bdae-4190-a1f3-62a17acee817');
