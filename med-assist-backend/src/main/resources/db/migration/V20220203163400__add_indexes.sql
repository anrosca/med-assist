create index if not exists teeth_patient_id_index on teeth(patient_id);

create index if not exists treatment_patient_id_index on treatments(patient_id);

create index if not exists treatment_doctor_id_index on treatments(doctor_id);
