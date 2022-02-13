import {Component, OnInit, ViewChild} from '@angular/core';
import {NGXLogger} from 'ngx-logger';
import {Title} from '@angular/platform-browser';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {NotificationService} from '../../core/services/notification.service';
import {MatSort} from '@angular/material/sort';
import {AppointmentService} from '../../core/services/appointment.service';

@Component({
    selector: 'app-appointments-list',
    templateUrl: './appointments-list.component.html',
    styleUrls: ['./appointments-list.component.css']
})
export class AppointmentsListComponent implements OnInit {
    displayedColumns: string[] = ['startDate', 'endDate', 'operation', 'doctor', 'patient', 'details'];
    dataSource;

    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    constructor(
        private notificationService: NotificationService,
        private appointmentService: AppointmentService,
        private titleService: Title
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

        this.titleService.setTitle('med-assist-client - Appointments');
        this.appointmentService.getAllAppointments()
            .subscribe(appointments => {
                    appointments.map(appointment => {
                        appointment.startDate = this.toLocalDate(appointment.startDate);
                        appointment.endDate = this.toLocalDate(appointment.endDate);
                        return appointment;
                    });
                    this.dataSource = new MatTableDataSource(appointments);
                    this.dataSource.sort = this.sort;
                    this.dataSource.paginator = this.paginator;
                    this.dataSource.filterPredicate = (data: any, filter) => {
                        const dataStr = JSON.stringify(data).toLowerCase();
                        return dataStr.indexOf(filter) != -1;
                    };
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });

    }

    private toLocalDate(dateString) {
        const now = new Date();
        const millisecondsPerMinute = 60000;
        return new Date(new Date(dateString).getTime() - now.getTimezoneOffset() * millisecondsPerMinute).toLocaleString('en-GB');
    }

}
