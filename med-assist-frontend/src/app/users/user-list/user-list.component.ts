import {Component, OnInit, ViewChild} from '@angular/core';
import {Title} from '@angular/platform-browser';

import {NotificationService} from '../../core/services/notification.service';
import {NGXLogger} from 'ngx-logger';
import {MatTableDataSource} from '@angular/material/table';
import {MatSort} from '@angular/material/sort';
import {UserService} from '../../core/services/user.service';
import {MatPaginator} from '@angular/material';

@Component({
    selector: 'app-user-list',
    templateUrl: './user-list.component.html',
    styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
    displayedColumns: string[] = ['id', 'firstName', 'lastName', 'username', 'email', 'authorities', 'enabled'];
    dataSource;

    @ViewChild(MatSort, {static: true}) sort: MatSort;
    @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;

    constructor(
        private logger: NGXLogger,
        private notificationService: NotificationService,
        private userService: UserService,
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

        this.titleService.setTitle('med-assist-client - Users');
        this.logger.log('Users loaded');
        this.userService.getAllUsers()
            .subscribe(users => {
                    this.dataSource = new MatTableDataSource(users);
                    this.dataSource.sort = this.sort;
                    this.dataSource.paginator = this.paginator;
                },
                error => {
                    const resMessage = error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });

    }
}
