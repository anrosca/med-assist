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

create index user_id_fk_index on user_authorities(user_id);

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
    phone_number varchar(20) not null,
    is_deleted bool not null default false
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

create table appointments
(
    id               varchar(100) primary key,
    operation        varchar(255) not null,
    doctor_id        varchar(100) not null,
    patient_id       varchar(100) not null,
    appointment_date date         not null,
    start_time       time         not null,
    end_time         time         not null,
    details          varchar(255),
    created_at timestamp not null,
    updated_at timestamp not null,
    primary_color varchar(10) not null,
    secondary_color varchar(10) not null,
    constraint doctor_fk foreign key (doctor_id) references doctors (id),
    constraint patient_fk foreign key (patient_id) references patients (id)
);

create index doctor_id_fk_index on appointments (doctor_id);
create index start_time_index on appointments(start_time);
create index end_time_index on appointments(end_time);

create table teeth
(
    id varchar(255) not null
        constraint teeth_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    extracted boolean not null,
    name varchar(255),
    patient_id varchar(255)
        constraint patient_fk references patients
);

create table treatments
(
    id varchar(255) not null
        constraint treatments_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    description varchar(255),
    price double precision,
    doctor_id varchar(255) not null
        constraint doctor_fk references doctors,
    patient_id varchar(255) not null
        constraint patient_fk references patients
);

create table treatment_tooth
(
    treatment_id varchar(255) not null
        constraint treatment_fk references treatments,
    tooth_id varchar(255) not null
        constraint teeth_fk references teeth,
    constraint treatment_tooth_pkey
        primary key (treatment_id, tooth_id)
);

create table file_records
(
    id varchar(255) not null
        constraint file_records_pkey
            primary key,
    created_at timestamp not null,
    updated_at timestamp not null,
    data oid,
    name varchar(255),
    type varchar(255),
    patient_id varchar(255) not null constraint patinet_fk references patients
);




