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
import {Doctor} from "../../core/model/doctor";
import {Patient} from "../../core/model/patient";
import {PatientService} from "../../core/services/patient.service";

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
    templateUrl: './dashboard-home.component.html',
    styleUrls: ['./dashboard-home.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush,
    providers: [
        {
            provide: CalendarEventTitleFormatter,
            useClass: CustomEventTitleFormatter,
        },
    ],
})
export class DashboardHomeComponent implements OnInit, AfterViewInit {
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
                            start: new Date(appointment.startDate),
                            end: new Date(appointment.endDate),
                            color: colors.blue,
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
                                        this.events = this.events.filter((iEvent) => iEvent !== event);
                                        this.handleEvent('Deleted', event);
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

    deleteEvent(eventToDelete: CalendarEvent) {
        this.events = this.events.filter((event) => event !== eventToDelete);
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
            width: '700px',
            data: {appointmentToCreate: this.appointmentToCreate},
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
}

@Component({
    selector: 'create-appointment-dialog',
    templateUrl: 'create-appointment-dialog.html',
})
export class CreateAppointmentDialog implements AfterViewInit {
    doctors: Doctor[] = [];
    patients: Patient[] = [];

    constructor(
        public dialogRef: MatDialogRef<CreateAppointmentDialog>,
        @Inject(MAT_DIALOG_DATA) public data: CreateAppointmentDialogData,
        private doctorService: DoctorService,
        private patientService: PatientService,
    ) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    ngAfterViewInit(): void {
        this.doctorService.getAllDoctors().subscribe(doctors => {
            this.doctors = doctors;
        })
        this.patientService.getAllPatients().subscribe(patients => {
            this.patients = patients;
        })
    }
}

export interface CreateAppointmentDialogData {
    appointmentToCreate: any;
}
