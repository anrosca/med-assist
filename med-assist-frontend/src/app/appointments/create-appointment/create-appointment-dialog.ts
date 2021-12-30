import {AfterViewInit, Component, Inject} from "@angular/core";
import {Doctor} from "../../core/model/doctor";
import {Patient} from "../../core/model/patient";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DoctorService} from "../../core/services/doctor.service";
import {PatientService} from "../../core/services/patient.service";

@Component({
    selector: 'create-appointment-dialog',
    templateUrl: '../create-appointment/create-appointment-dialog.html',
    styleUrls: ['create-appointment-dialog.css']
})
export class CreateAppointmentDialog implements AfterViewInit {
    doctors: Doctor[] = [];
    patients: Patient[] = [];

    constructor(
        public dialogRef: MatDialogRef<CreateAppointmentDialog>,
        @Inject(MAT_DIALOG_DATA) public data: CreateAppointmentDialogData,
        private doctorService: DoctorService,
        private patientService: PatientService,
    ) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    ngAfterViewInit(): void {
        this.doctorService.getAllDoctors().subscribe(doctors => {
            this.doctors = doctors;
        })
        this.patientService.getAllPatients().subscribe(patients => {
            this.patients = patients;
        })
    }
}

export interface CreateAppointmentDialogData {
    appointment: any;
}
