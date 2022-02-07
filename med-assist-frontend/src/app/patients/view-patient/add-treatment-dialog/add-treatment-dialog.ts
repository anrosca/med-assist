import {AfterViewInit, Component, Inject, OnInit, ViewChild} from '@angular/core';
import {Tooth} from '../../../core/model/tooth';
import {TeethService} from '../../../core/services/teeth.service';
import {NotificationService} from '../../../core/services/notification.service';
import {Patient} from '../../../core/model/patient';
import {DentalChart} from '../../../core/model/dental-chart';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {DoctorService} from '../../../core/services/doctor.service';
import {PatientService} from '../../../core/services/patient.service';
import {Doctor} from '../../../core/model/doctor';
import {ReplaySubject, Subject} from "rxjs";
import {FormControl} from "@angular/forms";
import {MatSelect} from "@angular/material/select";
import {take, takeUntil} from "rxjs/operators";

@Component({
    selector: 'app-add-treatment-dialog',
    templateUrl: './add-treatment-dialog.html',
    styleUrls: ['./add-treatment-dialog.css']
})
export class AddTreatmentDialog implements OnInit, AfterViewInit {

    doctors: Doctor[] = [];
    patients: Patient[] = [];

    dentalChart: DentalChart;
    treatedTeeth: TreatedTooth[] = [];
    markSelectedTeethAsExtracted = false;

    public filteredDoctors: ReplaySubject<Doctor[]> = new ReplaySubject<Doctor[]>(1);
    doctorCtrl: FormControl = new FormControl();
    doctorFilterCtrl: FormControl = new FormControl();
    protected _onDestroy = new Subject<void>();
    @ViewChild('doctorSingleSelect', {static: true}) doctorSingleSelect: MatSelect;

    constructor(private teethService: TeethService,
                private doctorService: DoctorService,
                private patientService: PatientService,
                private notificationService: NotificationService,
                public dialogRef: MatDialogRef<AddTreatmentDialog>,
                @Inject(MAT_DIALOG_DATA) public data: CreateTreatmentData,) {
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
        this.filteredDoctors
            .pipe(take(1), takeUntil(this._onDestroy))
            .subscribe(() => {
                this.doctorSingleSelect.compareWith = (a: Patient, b: Patient) => a && b && a.id === b.id;
            });
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
        this.teethService.getPatientTeeth(this.data.patient.id)
            .subscribe(teeth => {
                    const mappedTeeth = teeth as unknown as Tooth[];
                    this.dentalChart = {
                        UR1: mappedTeeth.filter((tooth) => tooth.code === 'UR1')[0],
                        UR2: mappedTeeth.filter((tooth) => tooth.code === 'UR2')[0],
                        UR3: mappedTeeth.filter((tooth) => tooth.code === 'UR3')[0],
                        UR4: mappedTeeth.filter((tooth) => tooth.code === 'UR4')[0],
                        UR5: mappedTeeth.filter((tooth) => tooth.code === 'UR5')[0],
                        UR6: mappedTeeth.filter((tooth) => tooth.code === 'UR6')[0],
                        UR7: mappedTeeth.filter((tooth) => tooth.code === 'UR7')[0],
                        UR8: mappedTeeth.filter((tooth) => tooth.code === 'UR8')[0],
                        UL1: mappedTeeth.filter((tooth) => tooth.code === 'UL1')[0],
                        UL2: mappedTeeth.filter((tooth) => tooth.code === 'UL2')[0],
                        UL3: mappedTeeth.filter((tooth) => tooth.code === 'UL3')[0],
                        UL4: mappedTeeth.filter((tooth) => tooth.code === 'UL4')[0],
                        UL5: mappedTeeth.filter((tooth) => tooth.code === 'UL5')[0],
                        UL6: mappedTeeth.filter((tooth) => tooth.code === 'UL6')[0],
                        UL7: mappedTeeth.filter((tooth) => tooth.code === 'UL7')[0],
                        UL8: mappedTeeth.filter((tooth) => tooth.code === 'UL8')[0],
                        LL1: mappedTeeth.filter((tooth) => tooth.code === 'LL1')[0],
                        LL2: mappedTeeth.filter((tooth) => tooth.code === 'LL2')[0],
                        LL3: mappedTeeth.filter((tooth) => tooth.code === 'LL3')[0],
                        LL4: mappedTeeth.filter((tooth) => tooth.code === 'LL4')[0],
                        LL5: mappedTeeth.filter((tooth) => tooth.code === 'LL5')[0],
                        LL6: mappedTeeth.filter((tooth) => tooth.code === 'LL6')[0],
                        LL7: mappedTeeth.filter((tooth) => tooth.code === 'LL7')[0],
                        LL8: mappedTeeth.filter((tooth) => tooth.code === 'LL8')[0],
                        LR1: mappedTeeth.filter((tooth) => tooth.code === 'LR1')[0],
                        LR2: mappedTeeth.filter((tooth) => tooth.code === 'LR2')[0],
                        LR3: mappedTeeth.filter((tooth) => tooth.code === 'LR3')[0],
                        LR4: mappedTeeth.filter((tooth) => tooth.code === 'LR4')[0],
                        LR5: mappedTeeth.filter((tooth) => tooth.code === 'LR5')[0],
                        LR6: mappedTeeth.filter((tooth) => tooth.code === 'LR6')[0],
                        LR7: mappedTeeth.filter((tooth) => tooth.code === 'LR7')[0],
                        LR8: mappedTeeth.filter((tooth) => tooth.code === 'LR8')[0]
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    selectTooth(tooth: Tooth) {
        console.log(this.treatedTeeth);
        tooth.isSelected = !tooth.isSelected;
        if (tooth.isSelected) {
            this.treatedTeeth.push({
                'toothId': tooth.id,
                'isExtracted': this.markSelectedTeethAsExtracted
            });
        } else {
            this.treatedTeeth = this.treatedTeeth.filter(treatedTooth => treatedTooth.toothId !== tooth.id);
        }
        this.data.treatment.treatedTeeth = this.treatedTeeth;
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

    onMarkSelectedTeethAsExtractedChange() {
        this.treatedTeeth.forEach(treatedTooth => treatedTooth.isExtracted = this.markSelectedTeethAsExtracted);
    }
}

export interface CreateTreatmentData {
    status: string;
    treatment: any;
    patient: Patient;
}

export interface TreatedTooth {
    toothId: string;
    isExtracted: boolean;
}
