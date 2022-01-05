import {AfterViewInit, ChangeDetectionStrategy, Component, Inject, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {NotificationService} from 'src/app/core/services/notification.service';
import {Title} from '@angular/platform-browser';
import {NGXLogger} from 'ngx-logger';
import {AuthenticationService} from 'src/app/core/services/auth.service';

import {
    CalendarEvent,
    CalendarEventAction,
    CalendarEventTimesChangedEvent,
    CalendarEventTitleFormatter,
    CalendarView
} from "angular-calendar";
import {Subject} from "rxjs";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {
    startOfDay,
    endOfDay,
    subDays,
    addDays,
    endOfMonth,
    isSameDay,
    isSameMonth,
    addHours,
} from 'date-fns';
import {AppointmentService} from "../../core/services/appointment.service";
import {MatTableDataSource} from "@angular/material/table";
import {faCoffee} from '@fortawesome/free-solid-svg-icons';
import {CustomEventTitleFormatter} from "../../shared/calendar/custom-event-title-formatter.provider";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {DoctorService} from "../../core/services/doctor.service";
import {CreateAppointmentDialog} from "../create-appointment/create-appointment-dialog";
import {ViewAppointmentDialog} from "../view-appointment/view-appointment-dialog";
import {ConfirmDialog, ConfirmDialogModel} from "../../shared/confirm-dialog/confirm-dialog.component";

const colors: any = {
    red: {
        primary: '#ad2121',
        secondary: '#FAE3E3',
    },
    purple: {
        primary: '#673AB7',
        secondary: '#fcfaed',
    },
    blue: {
        primary: '#1e90ff',
        secondary: '#D1E8FF',
    },
    yellow: {
        primary: '#e3bc08',
        secondary: '#FDF1BA',
    },
};

@Component({
    selector: 'app-dashboard-home',
    templateUrl: './appointments-calendar.component.html',
    styleUrls: ['./appointments-calendar.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [
        {
            provide: CalendarEventTitleFormatter,
            useClass: CustomEventTitleFormatter,
        },
    ],
})
export class AppointmentsCalendarComponent implements OnInit, AfterViewInit {
    @ViewChild('modalContent', {static: true}) modalContent: TemplateRef<any>;

    currentUser: any;
    faCoffee = faCoffee;

    view: CalendarView = CalendarView.Month;

    CalendarView = CalendarView;

    viewDate: Date = new Date();

    modalData: {
        action: string;
        event: CalendarEvent;
    };


    refresh = new Subject<void>();

    events: CalendarEvent[] = [];

    activeDayIsOpen: boolean = true;

    constructor(private notificationService: NotificationService,
                private authService: AuthenticationService,
                private titleService: Title,
                private logger: NGXLogger,
                private modal: NgbModal,
                private appointmentService: AppointmentService,
                private doctorService: DoctorService,
                public dialog: MatDialog) {

    }

    ngAfterViewInit(): void {

        this.events = [];
        this.appointmentService.getAllAppointments()
            .subscribe(appointments => {
                    appointments.forEach(appointment => {
                        var calendarEvent = {
                            title: appointment.operation,
                            start: AppointmentsCalendarComponent.toUtcDate(appointment.startDate),
                            end: AppointmentsCalendarComponent.toUtcDate(appointment.endDate),
                            color: appointment.color,
                            meta: {
                                id: appointment.id,
                                details: appointment.details,
                                doctor: appointment.doctor,
                                patient: appointment.patient,
                            },
                            actions: [
                                {
                                    label: '<i class="fa fa-pencil"></i> ',
                                    a11yLabel: 'Edit',
                                    onClick: ({event}: { event: CalendarEvent }): void => {
                                        this.handleEvent('Edited', event);
                                    },
                                },
                                {
                                    label: '<i class="fa fa-trash"></i> ',
                                    a11yLabel: 'Delete',
                                    onClick: ({event}: { event: CalendarEvent }): void => {
                                        this.deleteAppointment(event);
                                    },
                                },
                            ]
                        }
                        this.events.push(calendarEvent);
                    })
                    this.refresh.next();
                },
                error => {
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                });
    }

    private static toUtcDate(dateString) {
        const now = new Date();
        const millisecondsPerMinute = 60000;
        return new Date(new Date(dateString).getTime() - now.getTimezoneOffset() * millisecondsPerMinute)
    }

    dayClicked({date, events}: { date: Date; events: CalendarEvent[] }): void {
        if (isSameMonth(date, this.viewDate)) {
            if (
                (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
                events.length === 0
            ) {
                this.activeDayIsOpen = false;
            } else {
                this.activeDayIsOpen = true;
            }
            this.viewDate = date;
        }
    }

    eventTimesChanged({
                          event,
                          newStart,
                          newEnd,
                      }: CalendarEventTimesChangedEvent): void {
        this.events = this.events.map((iEvent) => {
            if (iEvent === event) {
                return {
                    ...event,
                    start: newStart,
                    end: newEnd,
                };
            }
            return iEvent;
        });
        this.handleEvent('Dropped or resized', event);
    }

    handleEvent(action: string, event: CalendarEvent): void {
        this.modalData = {event, action};
        this.modal.open(this.modalContent, {size: 'lg'});
    }

    addEvent(): void {
        this.events = [
            ...this.events,
            {
                title: 'New event',
                start: startOfDay(new Date()),
                end: endOfDay(new Date()),
                color: colors.red,
                draggable: true,
                resizable: {
                    beforeStart: true,
                    afterEnd: true,
                },
            },
        ];
    }


    setView(view: CalendarView) {
        this.view = view;
    }

    closeOpenMonthViewDay() {
        this.activeDayIsOpen = false;
    }

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        this.titleService.setTitle('med-assist-client - Dashboard');

        setTimeout(() => {
            this.notificationService.openSnackBar('Welcome!');
        });
    }

    appointmentToCreate: any = {
        title: '',
        details: '',
        doctorId: '',
        patientId: '',
        existingPatient: true,
        start: startOfDay(new Date()),
        end: endOfDay(new Date()),
        color: colors.blue,
        patientBirthDate: new Date(),
        patientFirstName: '',
        patientLastName: '',
        patientPhoneNumber: '',
        draggable: true,
        resizable: {
            beforeStart: true,
            afterEnd: true,
        },
    };

    openCreateAppointmentDialog(): void {
        const dialogRef = this.dialog.open(CreateAppointmentDialog, {
            width: 'auto',
            data: {appointment: this.appointmentToCreate}
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
            console.log(result)

            this.appointmentService.createAppointment(result).subscribe(() => {
                this.ngAfterViewInit();
                this.refresh.next();
            }, error => {
                console.log(error)
                const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                this.notificationService.openSnackBar(resMessage);
            })
        });
    }


    deleteAppointment(eventToDelete: CalendarEvent) {
        let patient = eventToDelete.meta.patient;
        const dialogRef = this.dialog.open(ConfirmDialog, {
            data: {title: 'Delete appointment', message: 'Are you sure you want to delete ' + patient.firstName + ' ' + patient.lastName + '\'s appointment?'}
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.appointmentService.deleteAppointment(eventToDelete.meta.id).subscribe(() => {
                    this.events = this.events.filter((event) => event !== eventToDelete);
                    this.refresh.next();
                }, error => {
                    console.log(error)
                    const resMessage = error.error.messages || error.message || error.error.message || error.toString();
                    this.notificationService.openSnackBar(resMessage);
                })
            }
        });
    }

    viewAppointment(event: CalendarEvent): void {
        const dialogRef = this.dialog.open(ViewAppointmentDialog, {
            width: 'auto',
            data: {appointment: event}
        });

        dialogRef.afterClosed().subscribe(() => {
        });
    }
}

