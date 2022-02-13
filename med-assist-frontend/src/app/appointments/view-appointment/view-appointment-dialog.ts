import {AfterViewInit, Component, Inject} from '@angular/core';
import {Doctor} from '../../core/model/doctor';
import {Patient} from '../../core/model/patient';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DoctorService} from '../../core/services/doctor.service';
import {PatientService} from '../../core/services/patient.service';

@Component({
    selector: 'view-appointment-dialog',
    templateUrl: 'view-appointment-dialog.html',
    styleUrls: ['view-appointment-dialog.css']
})
export class ViewAppointmentDialog implements AfterViewInit {
    doctors: Doctor[] = [];
    patients: Patient[] = [];

    constructor(
        public dialogRef: MatDialogRef<ViewAppointmentDialog>,
        @Inject(MAT_DIALOG_DATA) public data: ViewAppointmentDialogData,
        private doctorService: DoctorService,
        private patientService: PatientService,
    ) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    ngAfterViewInit(): void {
        this.data.appointment.start = this.toLocalDate(this.data.appointment.start);
        this.data.appointment.end = this.toLocalDate(this.data.appointment.end);
        this.doctorService.getAllDoctors().subscribe(doctors => {
            this.doctors = doctors;
        });
        this.patientService.getAllPatients().subscribe(patients => {
            this.patients = patients;
        });
    }

    private toLocalDate(dateString) {
        return new Date(dateString).toLocaleString('en-GB');
    }
}

export interface ViewAppointmentDialogData {
    appointment: any;
}
