import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ViewAppointmentDialogData} from "../../appointments/view-appointment/view-appointment-dialog";

@Component({
    selector: 'app-view-patient',
    templateUrl: './view-patient-dialog.html',
    styleUrls: ['./view-patient-dialog.css']
})
export class ViewPatientDialog implements OnInit {

    constructor(public dialogRef: MatDialogRef<ViewPatientDialog>,
                @Inject(MAT_DIALOG_DATA) public data: ViewPatientDialogData,) {
    }

    ngOnInit(): void {
    }
}

export interface ViewPatientDialogData {
    patient: any;
}
