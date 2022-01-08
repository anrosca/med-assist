import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Doctor} from '../../core/model/doctor';
import {Patient} from '../../core/model/patient';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DoctorService} from '../../core/services/doctor.service';
import {PatientService} from '../../core/services/patient.service';
import {FormControl} from '@angular/forms';
import {ReplaySubject, Subject} from 'rxjs';
import {take, takeUntil} from 'rxjs/operators';
import {MatSelect} from '@angular/material/select';

@Component({
    selector: 'app-create-appointment-dialog',
    templateUrl: 'create-appointment-dialog.html',
    styleUrls: ['create-appointment-dialog.css']
})
export class CreateAppointmentDialog implements AfterViewInit, OnInit {
    doctors: Doctor[] = [];
    patients: Patient[] = [];
    patientCtrl: FormControl = new FormControl();
    patientFilterCtrl: FormControl = new FormControl();
    public filteredPatients: ReplaySubject<Patient[]> = new ReplaySubject<Patient[]>(1);
    @ViewChild('patientSingleSelect', {static: true}) patientSingleSelect: MatSelect;
    public filteredDoctors: ReplaySubject<Doctor[]> = new ReplaySubject<Doctor[]>(1);
    doctorCtrl: FormControl = new FormControl();
    doctorFilterCtrl: FormControl = new FormControl();
    protected _onDestroy = new Subject<void>();
    @ViewChild('doctorSingleSelect', {static: true}) doctorSingleSelect: MatSelect;


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

    protected setInitialValue() {
        this.filteredPatients
            .pipe(take(1), takeUntil(this._onDestroy))
            .subscribe(() => {
                this.patientSingleSelect.compareWith = (a: Patient, b: Patient) => a && b && a.id === b.id;
            });

        this.filteredDoctors
            .pipe(take(1), takeUntil(this._onDestroy))
            .subscribe(() => {
                this.doctorSingleSelect.compareWith = (a: Patient, b: Patient) => a && b && a.id === b.id;
            });
    }

    ngAfterViewInit(): void {
        this.setInitialValue();
    }

    private filterPatients() {
        if (!this.patients) {
            return;
        }
        // get the search keyword
        let search = this.patientFilterCtrl.value;
        console.log(search);
        console.log('nanana');
        if (!search) {
            this.filteredPatients.next(this.patients.slice());
            return;
        } else {
            search = search.toLowerCase();
        }

        this.filteredPatients.next(
            this.patients.filter(patient => (patient.firstName + ' ' + patient.lastName).toLowerCase().indexOf(search) > -1)
        );
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
            this.filteredPatients.next(this.patients.slice());
        });
        this.patientFilterCtrl.valueChanges
            .pipe(takeUntil(this._onDestroy))
            .subscribe(() => {
                this.filterPatients();
            });

    }
}

export interface CreateAppointmentDialogData {
    appointment: any;
}
