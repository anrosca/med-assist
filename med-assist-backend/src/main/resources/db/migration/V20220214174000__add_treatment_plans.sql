create table if not exists treatment_plans
(
    id         varchar(255) not null,
    created_at timestamp    not null,
    updated_at timestamp    not null,
    plan_name  varchar(255),
    patient_id varchar(255) not null,
    primary key (id),
    constraint treatment_plan_patient_id_fk foreign key (patient_id) references patients (id)
);

create table if not exists treatment_plan_items
(
    id                varchar(255) not null,
    created_at        timestamp    not null,
    updated_at        timestamp    not null,
    completed_at      date,
    description       varchar(255) not null,
    scheduled_at      date         not null,
    status            varchar(255) not null,
    doctor_id         varchar(255) not null,
    treatment_plan_id varchar(255) not null,
    primary key (id),
    constraint item_doctor_fk foreign key (doctor_id) references doctors (id),
    constraint treatment_plan_fk foreign key (treatment_plan_id) references treatment_plans (id)
);

create table if not exists treatment_plan_items_teeth
(
    treatment_plan_item_id varchar(255) not null,
    teeth_id               varchar(255) not null,
    primary key (treatment_plan_item_id, teeth_id),
    constraint teeth_id_unique unique (teeth_id),
    constraint treatment_plan_item_id_fk foreign key (treatment_plan_item_id) references treatment_plan_items (id)
);
