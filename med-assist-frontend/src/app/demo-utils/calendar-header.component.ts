import {Component, Input, Output, EventEmitter} from '@angular/core';
import {CalendarView} from 'angular-calendar';

@Component({
    selector: 'mwl-demo-utils-calendar-header',
    styleUrls: ['./calendar-header.component.css'],
    template: `
            <div class="row text-center">
                <div class="col-md-4">
                    <mat-button-toggle-group name="days">
                        <mat-button-toggle mwlCalendarPreviousView
                                           [view]="view"
                                           [(viewDate)]="viewDate"
                                           (viewDateChange)="viewDateChange.next(viewDate)">Previous
                        </mat-button-toggle>
                        <mat-button-toggle style="background: #673ab7; color: white;" mwlCalendarToday
                                           [(viewDate)]="viewDate"
                                           (viewDateChange)="viewDateChange.next(viewDate)">Today
                        </mat-button-toggle>
                        <mat-button-toggle mwlCalendarNextView
                                           [view]="view"
                                           [(viewDate)]="viewDate"
                                           (viewDateChange)="viewDateChange.next(viewDate)">Next
                        </mat-button-toggle>
                    </mat-button-toggle-group>
                </div>
                <div class="col-md-4">
                    <h3>{{ viewDate | calendarDate: view + 'ViewTitle':locale }}</h3>
                </div>
                <div class="col-md-4">
                    <mat-button-toggle-group name="days">
                        <mat-button-toggle (click)="viewChange.emit(CalendarView.Month)"
                                           [checked]="view === CalendarView.Month">Month
                        </mat-button-toggle>
                        <mat-button-toggle (click)="viewChange.emit(CalendarView.Week)"
                                           [checked]="view === CalendarView.Week">Week
                        </mat-button-toggle>
                        <mat-button-toggle (click)="viewChange.emit(CalendarView.Day)"
                                           [checked]="view === CalendarView.Day">Day
                        </mat-button-toggle>
                    </mat-button-toggle-group>
                </div>
            </div>

        <br/>
    `,
})
export class CalendarHeaderComponent {
    @Input() view: CalendarView;

    @Input() viewDate: Date;

    @Input() locale: string = 'en';

    @Output() viewChange = new EventEmitter<CalendarView>();

    @Output() viewDateChange = new EventEmitter<Date>();

    CalendarView = CalendarView;
}
