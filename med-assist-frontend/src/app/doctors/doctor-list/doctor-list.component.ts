import {Component, OnInit, ViewChild} from '@angular/core';
import {Title} from '@angular/platform-browser';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {DoctorService} from '../../core/services/doctor.service';
import {NotificationService} from '../../core/services/notification.service';
import {MatSort} from '@angular/material/sort';

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
        private notificationService: NotificationService,
        private doctorService: DoctorService,
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
        this.doctorService.getAllDoctors()
            .subscribe(doctors => {
                    this.dataSource = new MatTableDataSource(doctors);
                    this.dataSource.sort = this.sort;
                    this.dataSource.paginator = this.paginator;
                    this.dataSource.filterPredicate = (data: any, filter) => {
                        const dataStr = JSON.stringify(data).toLowerCase();
                        return dataStr.indexOf(filter) != -1;
                    }
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });

    }
}
