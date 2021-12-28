import {Doctor} from "./doctor";
import {Patient} from "./patient";

export interface Appointment {
    id: string;
    startDate: string;
    endDate: string;
    operation: string;
    doctor: Doctor;
    patient: Patient;
    details: string;
}
