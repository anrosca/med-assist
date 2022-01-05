import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {TreatmentService} from '../../core/services/treatment.service';
import {NotificationService} from '../../core/services/notification.service';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {MatPaginator} from '@angular/material/paginator';
import {AddTreatmentDialog} from './add-treatment-dialog/add-treatment-dialog';

@Component({
    selector: 'app-view-patient',
    templateUrl: './view-patient-dialog.html',
    styleUrls: ['./view-patient-dialog.css']
})
export class ViewPatientDialog implements OnInit {

    constructor(public dialogRef: MatDialogRef<ViewPatientDialog>,
                @Inject(MAT_DIALOG_DATA) public data: ViewPatientDialogData,
                private treatmentService: TreatmentService,
                private notificationService: NotificationService,
                public dialog: MatDialog) {
    }

    treatmentsDisplayedColumns: string[] = ['description', 'doctor', 'teeth', 'price', 'createdAt'];
    treatmentsDataSource;
    @ViewChild(MatSort, {static: true}) treatmentsSort: MatSort;
    @ViewChild(MatPaginator, {static: true}) treatmentsPaginator: MatPaginator;

    ngOnInit(): void {
        this.treatmentService.getTreatmentsByPatientId(this.data.patient.id)
            .subscribe(treatments => {
                    this.treatmentsDataSource = new MatTableDataSource(treatments);
                    this.treatmentsDataSource.sort = this.treatmentsSort;
                    this.treatmentsDataSource.paginator = this.treatmentsPaginator;
                    this.treatmentsDataSource.filterPredicate = (data: any, filter) => {
                        const dataStr = JSON.stringify(data).toLowerCase();
                        return dataStr.indexOf(filter) != -1;
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    applyTreatmentsFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.treatmentsDataSource.filter = filterValue.trim().toLowerCase();

        if (this.treatmentsDataSource.paginator) {
            this.treatmentsDataSource.paginator.firstPage();
        }
    }

    openAddTreatmentDialog() {
        const dialogRef = this.dialog.open(AddTreatmentDialog, {
            width: 'auto',
            data: {treatment: {}, patient: this.data.patient}
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
            console.log(result);

            this.treatmentService.createTreatment({
                doctorId: result.doctorId,
                description: result.description,
                patientId: this.data.patient.id,
                price: result.price,
                teethIds: result.teethIds
            }).subscribe(() => {
                this.ngOnInit();
            }, error => {
                console.log(error);
                const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                this.notificationService.openSnackBar(resMessage);
            });
        });
    }
}

export interface ViewPatientDialogData {
    patient: any;
}
