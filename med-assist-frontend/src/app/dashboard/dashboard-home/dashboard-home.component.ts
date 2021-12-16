import {Component, OnInit} from '@angular/core';
import {NotificationService} from 'src/app/core/services/notification.service';
import {Title} from '@angular/platform-browser';
import {NGXLogger} from 'ngx-logger';
import {AuthenticationService} from 'src/app/core/services/auth.service';
import {CalendarEvent, CalendarView} from "angular-calendar";


@Component({
    selector: 'app-dashboard-home',
    templateUrl: './dashboard-home.component.html',
    styleUrls: ['./dashboard-home.component.css']
})
export class DashboardHomeComponent implements OnInit {
    currentUser: any;

    view: CalendarView = CalendarView.Month;

    viewDate: Date = new Date();

    events: CalendarEvent[] = [];

    constructor(private notificationService: NotificationService,
                private authService: AuthenticationService,
                private titleService: Title,
                private logger: NGXLogger) {
    }

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        this.titleService.setTitle('med-assist-client - Dashboard');
        this.logger.log('Dashboard loaded');

        setTimeout(() => {
            this.notificationService.openSnackBar('Welcome!');
        });
    }
}
