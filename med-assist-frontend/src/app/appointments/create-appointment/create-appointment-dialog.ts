import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Doctor} from '../../core/model/doctor';
import {Patient} from '../../core/model/patient';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {DoctorService} from '../../core/services/doctor.service';
import {PatientService} from '../../core/services/patient.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ReplaySubject, Subject} from 'rxjs';
import {take, takeUntil} from 'rxjs/operators';
import {MatSelect} from '@angular/material/select';
import {ViewPatientDialog} from '../../patients/view-patient/view-patient-dialog';
import {NotificationService} from '../../core/services/notification.service';

@Component({
    selector: 'app-create-appointment-dialog',
    templateUrl: 'create-appointment-dialog.html',
    styleUrls: ['create-appointment-dialog.css']
})
export class CreateAppointmentDialog implements AfterViewInit, OnInit {
    doctors: Doctor[] = [];
    patients: Patient[] = [];
    patientFilterCtrl: FormControl = new FormControl();
    public filteredPatients: ReplaySubject<Patient[]> = new ReplaySubject<Patient[]>(1);
    @ViewChild('patientSingleSelect', {static: true}) patientSingleSelect: MatSelect;
    public filteredDoctors: ReplaySubject<Doctor[]> = new ReplaySubject<Doctor[]>(1);
    doctorFilterCtrl: FormControl = new FormControl();
    protected _onDestroy = new Subject<void>();
    @ViewChild('doctorSingleSelect', {static: true}) doctorSingleSelect: MatSelect;
    form: FormGroup;

    constructor(
        public dialogRef: MatDialogRef<CreateAppointmentDialog>,
        @Inject(MAT_DIALOG_DATA) public data: CreateAppointmentDialogData,
        private doctorService: DoctorService,
        private patientService: PatientService,
        public dialog: MatDialog,
        private notificationService: NotificationService
    ) {
    }

    onNoClick(): void {
        this.data.status = 'Closed';
        this.dialogRef.close(this.data);
    }

    onSubmit(): void {
        this.data.status = 'Submitted';
        this.dialogRef.close(this.data);
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
        this.setUpValidationRules();
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

    viewPatient(patientId) {
        this.patientService.getPatientById(patientId).subscribe((patient) => {
            const dialogRef = this.dialog.open(ViewPatientDialog, {
                width: '95%',
                height: '95%',
                data: {patient: patient}
            });

            dialogRef.afterClosed().subscribe(() => {
            });
        }, error => {
            console.log(error);
            const resMessage = error.error.messages || error.message || error.error.message || error.toString();
            this.notificationService.openSnackBar(resMessage);
        });
    }

    private setUpValidationRules() {
        this.form = new FormGroup({
            title: new FormControl(this.data.appointment.title, Validators.required),
            color: new FormControl(this.data.appointment.color.primary),
            details: new FormControl(this.data.appointment.details, Validators.required),
            doctor: new FormControl(this.data.appointment.doctorId, Validators.required),
            startDate: new FormControl(this.data.appointment.start, Validators.required),
            endDate: new FormControl(this.data.appointment.end, Validators.required),
            existingPatient: new FormControl(this.data.appointment.existingPatient),
            patient: new FormControl(this.data.appointment.patientId, Validators.required),
            patientFirstName: new FormControl(this.data.appointment.patientFirstName),
            patientLastName: new FormControl(this.data.appointment.patientLastName),
            patientPhoneNumber: new FormControl(this.data.appointment.patientPhoneNumber),
            patientBirthDate: new FormControl(this.data.appointment.patientBirthDate),
            patientSource: new FormControl(this.data.appointment.patientSource)
        });
        this.form.get('title').valueChanges
            .subscribe(val => { this.data.appointment.title = val; });
        this.form.get('color').valueChanges
            .subscribe(val => { this.data.appointment.color.primary = val; });
        this.form.get('details').valueChanges
            .subscribe(val => { this.data.appointment.details = val; });
        this.form.get('doctor').valueChanges
            .subscribe(val => { this.data.appointment.doctorId = val; });
        this.form.get('startDate').valueChanges
            .subscribe(val => { this.data.appointment.start = val; });
        this.form.get('endDate').valueChanges
            .subscribe(val => { this.data.appointment.end = val; });
        this.form.get('existingPatient').valueChanges
            .subscribe(val => { this.data.appointment.existingPatient = val; });
        this.form.get('patient').valueChanges
            .subscribe(val => { this.data.appointment.patientId = val; });
        this.form.get('patientFirstName').valueChanges
            .subscribe(val => { this.data.appointment.patientFirstName = val; });
        this.form.get('patientLastName').valueChanges
            .subscribe(val => { this.data.appointment.patientLastName = val; });
        this.form.get('patientPhoneNumber').valueChanges
            .subscribe(val => { this.data.appointment.patientPhoneNumber = val; });
        this.form.get('patientBirthDate').valueChanges
            .subscribe(val => { this.data.appointment.patientBirthDate = val; });
        this.form.get('patientSource').valueChanges
            .subscribe(val => { this.data.appointment.patientSource = val; });
    }

    onPatientTypeChange() {
        if (!this.data.appointment.existingPatient) {
            this.form.get('patientFirstName').setValidators(Validators.required);
            this.form.get('patientLastName').setValidators(Validators.required);
            this.form.get('patientPhoneNumber').setValidators(Validators.required);
            this.form.get('patientBirthDate').setValidators(Validators.required);
            this.form.get('patientSource').setValidators(Validators.required);
            this.form.get('patient').clearValidators();
            this.form.get('patient').updateValueAndValidity();
        } else {
            this.form.get('patientFirstName').clearValidators();
            this.form.get('patientLastName').clearValidators();
            this.form.get('patientPhoneNumber').clearValidators();
            this.form.get('patientBirthDate').clearValidators();
            this.form.get('patientSource').clearValidators();
            this.form.get('patient').setValidators(Validators.required);
            this.form.get('patient').updateValueAndValidity();
        }
    }
}

export interface CreateAppointmentDialogData {
    status: string;
    appointment: any;
}
