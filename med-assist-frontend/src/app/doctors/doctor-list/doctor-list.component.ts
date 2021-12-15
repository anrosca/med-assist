import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort} from "@angular/material/sort";
import {NGXLogger} from "ngx-logger";
import {NotificationService} from "../../core/services/notification.service";
import {UserService} from "../../core/services/user.service";
import {Title} from "@angular/platform-browser";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {DoctorService} from "../../core/services/doctor.service";

@Component({
  selector: 'app-doctor-list',
  templateUrl: './doctor-list.component.html',
  styleUrls: ['./doctor-list.component.css']
})
export class DoctorListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'firstName', 'lastName', 'username', 'email', 'authorities', 'mobile', 'specialty', 'enabled'];
  dataSource;

  @ViewChild(MatSort, {static: true}) sort: MatSort;
  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

  constructor(
      private logger: NGXLogger,
      private notificationService: NotificationService,
      private userService: DoctorService,
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

    this.titleService.setTitle('med-assist-client - Doctors');
    this.userService.getAllDoctors()
        .subscribe(doctors  => {
              this.dataSource = new MatTableDataSource(doctors);
              this.dataSource.sort = this.sort;
              this.dataSource.paginator = this.paginator;
            },
            error => {
              const resMessage = error.message || error.error.message || error.toString();
              this.notificationService.openSnackBar(resMessage);
            });

  }
}
