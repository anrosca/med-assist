import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CreateAppointmentDialogData} from "../../appointments/create-appointment/create-appointment-dialog";
import {PatientService} from "../../core/services/patient.service";

interface CreatePatientDialogData {
    patient: any;
    status: string;
}

@Component({
    selector: 'app-create-patient-dialog',
    templateUrl: './create-patient-dialog.html',
    styleUrls: ['./create-patient-dialog.css']
})
export class CreatePatientDialog implements OnInit {

    constructor(public dialogRef: MatDialogRef<CreatePatientDialog>,
                @Inject(MAT_DIALOG_DATA) public data: CreatePatientDialogData,
                private patientService: PatientService) {
    }

    onNoClick(): void {
        this.data.status = 'Closed';
        this.dialogRef.close(this.data);
    }

    onSubmit() {
        this.data.status = 'Submitted';
        this.dialogRef.close(this.data);
    }

    ngOnInit(): void {
    }

}
