import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Doctor} from '../../../core/model/doctor';
import {Patient} from '../../../core/model/patient';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DoctorService} from '../../../core/services/doctor.service';
import {PatientService} from '../../../core/services/patient.service';
import {Tooth} from '../../../core/model/tooth';
import {ReplaySubject, Subject} from "rxjs";
import {FormControl} from "@angular/forms";
import {MatSelect} from "@angular/material/select";
import {take, takeUntil} from "rxjs/operators";

@Component({
  selector: 'app-add-treatment-dialog',
  templateUrl: './add-tooth-treatment-dialog.html',
  styleUrls: ['./add-tooth-treatment-dialog.css']
})
export class AddToothTreatmentDialog implements OnInit, AfterViewInit {
  doctors: Doctor[] = [];
  patients: Patient[] = [];

  public filteredDoctors: ReplaySubject<Doctor[]> = new ReplaySubject<Doctor[]>(1);
  doctorCtrl: FormControl = new FormControl();
  doctorFilterCtrl: FormControl = new FormControl();
  protected _onDestroy = new Subject<void>();
  @ViewChild('doctorSingleSelect', {static: true}) doctorSingleSelect: MatSelect;

  constructor(
      public dialogRef: MatDialogRef<AddToothTreatmentDialog>,
      @Inject(MAT_DIALOG_DATA) public data: CreateToothTreatmentData,
      private doctorService: DoctorService,
      private patientService: PatientService,
  ) {
  }

  protected setInitialValue() {
    this.filteredDoctors
        .pipe(take(1), takeUntil(this._onDestroy))
        .subscribe(() => {
          this.doctorSingleSelect.compareWith = (a: Patient, b: Patient) => a && b && a.id === b.id;
        });
  }

  onNoClick(): void {
    this.dialogRef.close({ data: 'closed' });
  }


  onSubmit(): void {
    this.dialogRef.close({ data: this.data.treatment });
  }

  ngOnInit(): void {
    this.doctorService.getAllDoctors().subscribe(doctors => {
      this.doctors = doctors;
      this.filteredDoctors.next(this.doctors.slice());
    });
    this.doctorFilterCtrl.valueChanges
        .pipe(takeUntil(this._onDestroy))
        .subscribe(() => {
          this.filterDoctors();
        });
    this.patientService.getAllPatients().subscribe(patients => {
      this.patients = patients;
    });
  }


  ngAfterViewInit(): void {
    this.setInitialValue();
  }

  private filterDoctors() {
    if (!this.doctors) {
      return;
    }
    // get the search keyword
    let search = this.doctorFilterCtrl.value;
    console.log(search);
    console.log('nanana');
    if (!search) {
      this.filteredDoctors.next(this.doctors.slice());
      return;
    } else {
      search = search.toLowerCase();
    }

    this.filteredDoctors.next(
        this.doctors.filter(doctor => (doctor.firstName + ' ' + doctor.lastName).toLowerCase().indexOf(search) > -1)
    );
  }
}

export interface CreateToothTreatmentData {
  treatment: any;
  patient: Patient;
  tooth: Tooth;
}
