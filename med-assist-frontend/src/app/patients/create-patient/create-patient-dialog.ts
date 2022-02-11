import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {PatientService} from '../../core/services/patient.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';

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
    form: FormGroup;

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
        this.form = new FormGroup({
            firstName: new FormControl('', Validators.required),
            lastName: new FormControl('', Validators.required),
            phoneNumber: new FormControl('', Validators.required),
            birthDate: new FormControl('', Validators.required),
            source: new FormControl('', Validators.required),
        });
        this.form.get('firstName').valueChanges
            .subscribe(val => { this.data.patient.firstName = val; });
        this.form.get('lastName').valueChanges
            .subscribe(val => { this.data.patient.lastName = val; });
        this.form.get('phoneNumber').valueChanges
            .subscribe(val => { this.data.patient.phoneNumber = val; });
        this.form.get('birthDate').valueChanges
            .subscribe(val => { this.data.patient.birthDate = val; });
        this.form.get('source').valueChanges
            .subscribe(val => { this.data.patient.source = val; });
    }
}
