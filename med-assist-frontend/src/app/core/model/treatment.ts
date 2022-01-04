import {Doctor} from "./doctor";
import {Patient} from "./patient";
import {Tooth} from "./tooth";

export interface Treatment {
    id: string;
    description: string;
    price: string;
    doctor: Doctor;
    patient: Patient;
    teeth: Tooth[];
    createdAt: Date;
}
