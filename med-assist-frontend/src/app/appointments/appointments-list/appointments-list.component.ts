import {Component, OnInit, ViewChild} from '@angular/core';
import {NGXLogger} from "ngx-logger";
import {Title} from "@angular/platform-browser";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {DoctorService} from "../../core/services/doctor.service";
import {NotificationService} from "../../core/services/notification.service";
import {MatSort} from "@angular/material/sort";
import {AppointmentService} from "../../core/services/appointment.service";

@Component({
  selector: 'app-appointments-list',
  templateUrl: './appointments-list.component.html',
  styleUrls: ['./appointments-list.component.css']
})
export class AppointmentsListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'startDate', 'endDate', 'operation', 'doctor', 'patient', 'details'];
  dataSource;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(
      private logger: NGXLogger,
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
        .subscribe(appointments  => {
              this.dataSource = new MatTableDataSource(appointments);
              this.dataSource.sort = this.sort;
              this.dataSource.paginator = this.paginator;
            },
            error => {
              const resMessage = error.error.messages || error.message || error.error.message || error.toString();
              this.notificationService.openSnackBar(resMessage);
            });

  }


}
