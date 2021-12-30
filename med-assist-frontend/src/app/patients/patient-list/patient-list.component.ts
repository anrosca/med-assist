import {Component, OnInit, ViewChild} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {NotificationService} from "../../core/services/notification.service";
import {MatSort} from "@angular/material/sort";
import {PatientService} from "../../core/services/patient.service";
import {CalendarEvent} from "angular-calendar";
import {ViewAppointmentDialog} from "../../appointments/view-appointment/view-appointment-dialog";
import {MatDialog} from "@angular/material/dialog";
import {ViewPatientDialog} from "../view-patient/view-patient-dialog";

@Component({
    selector: 'app-patient-list',
    templateUrl: './patient-list.component.html',
    styleUrls: ['./patient-list.component.css']
})
export class PatientListComponent implements OnInit {
    displayedColumns: string[] = ['id', 'firstName', 'lastName', 'phoneNumber', 'birthDate', 'action'];
    dataSource;

    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    constructor(
        private notificationService: NotificationService,
        private patientService: PatientService,
        private titleService: Title,
        public dialog: MatDialog
    ) {
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();

        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

    ngOnInit() {

        this.titleService.setTitle('med-assist-client - Patients');
        this.patientService.getAllPatients()
            .subscribe(patients => {
                    this.dataSource = new MatTableDataSource(patients);
                    this.dataSource.sort = this.sort;
                    this.dataSource.paginator = this.paginator;
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });

    }

    viewPatient(patient) {
        const dialogRef = this.dialog.open(ViewPatientDialog, {
            width: '95%',
            height: '95%',
            data: {patient: patient}
        });

        dialogRef.afterClosed().subscribe(() => {
        });
    }
}
