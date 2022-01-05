import {AfterViewInit, Component, Inject} from '@angular/core';
import {Doctor} from '../../../core/model/doctor';
import {Patient} from '../../../core/model/patient';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DoctorService} from '../../../core/services/doctor.service';
import {PatientService} from '../../../core/services/patient.service';
import {Tooth} from '../../../core/model/tooth';

@Component({
  selector: 'app-add-treatment-dialog',
  templateUrl: './add-tooth-treatment-dialog.html',
  styleUrls: ['./add-tooth-treatment-dialog.css']
})
export class AddToothTreatmentDialog implements AfterViewInit {
  doctors: Doctor[] = [];
  patients: Patient[] = [];

  constructor(
      public dialogRef: MatDialogRef<AddToothTreatmentDialog>,
      @Inject(MAT_DIALOG_DATA) public data: CreateToothTreatmentData,
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
    });
    this.patientService.getAllPatients().subscribe(patients => {
      this.patients = patients;
    });
  }
}

export interface CreateToothTreatmentData {
  treatment: any;
  patient: Patient;
  tooth: Tooth;
}
