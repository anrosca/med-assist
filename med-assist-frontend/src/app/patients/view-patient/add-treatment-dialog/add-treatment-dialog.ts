import {Component, Inject, OnInit} from '@angular/core';
import {Tooth} from "../../../core/model/tooth";
import {TeethService} from "../../../core/services/teeth.service";
import {NotificationService} from "../../../core/services/notification.service";
import {Patient} from "../../../core/model/patient";
import {DentalChart} from "../../../core/model/dental-chart";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CreateToothTreatmentData} from "../add-tooth-treatment-dialog/add-tooth-treatment-dialog";
import {DoctorService} from "../../../core/services/doctor.service";
import {PatientService} from "../../../core/services/patient.service";
import {Doctor} from "../../../core/model/doctor";

@Component({
    selector: 'app-add-treatment-dialog',
    templateUrl: './add-treatment-dialog.html',
    styleUrls: ['./add-treatment-dialog.css']
})
export class AddTreatmentDialog implements OnInit {

    doctors: Doctor[] = [];
    patients: Patient[] = [];

    dentalChart: DentalChart;
    teethIds: string[] = [];

    constructor(private teethService: TeethService,
                private doctorService: DoctorService,
                private patientService: PatientService,
                private notificationService: NotificationService,
                public dialogRef: MatDialogRef<AddTreatmentDialog>,
                @Inject(MAT_DIALOG_DATA) public data: CreateTreatmentData,) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    ngOnInit(): void {
        this.doctorService.getAllDoctors().subscribe(doctors => {
            this.doctors = doctors;
        })
        this.patientService.getAllPatients().subscribe(patients => {
            this.patients = patients;
        })
        this.teethService.getPatientTeeth(this.data.patient.id)
            .subscribe(teeth => {
                    var mappedTeeth = teeth as unknown as Tooth[];
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
                    }
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    selectTooth(tooth: Tooth) {
        console.log(this.teethIds)
        tooth.isSelected = !tooth.isSelected;
        if (tooth.isSelected) {
            this.teethIds.push(tooth.id);
        } else {
            this.teethIds = this.teethIds.filter(id => id !== tooth.id)
        }
        this.data.treatment.teethIds = this.teethIds;
    }
}

export interface CreateTreatmentData {
    treatment: any;
    patient: Patient;
}
