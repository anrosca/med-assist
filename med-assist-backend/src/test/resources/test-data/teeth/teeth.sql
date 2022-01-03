truncate treatment_tooth cascade ;
truncate teeth cascade;
truncate treatments cascade ;

INSERT INTO teeth (id, created_at, updated_at, extracted, name, patient_id) VALUES ('e29c2861-bdae-4190-a1f3-62a17acee817', '2022-01-02 18:57:54.562174', '2022-01-02 18:57:54.562174', false, 'UR1', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO treatments (id, created_at, updated_at, description, price, doctor_id, patient_id) VALUES ('ff01f8ab-2ce9-46db-87be-a00142830a05', '2022-01-02 23:04:30.908247', '2022-01-02 23:04:30.908247', 'Veneers', 2000, '15297b89-045a-4daa-998f-5995fd44da3e', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO treatment_tooth (treatment_id, tooth_id) VALUES ('ff01f8ab-2ce9-46db-87be-a00142830a05', 'e29c2861-bdae-4190-a1f3-62a17acee817');
