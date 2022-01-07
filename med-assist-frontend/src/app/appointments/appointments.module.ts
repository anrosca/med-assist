import {CommonModule} from '@angular/common';

import {AppointmentsRoutingModule} from './appointments-routing.module';
import {SharedModule} from '../shared/shared.module';
import {AppointmentsCalendarComponent} from './appointments-calendar/appointments-calendar.component';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from '@angular/core';
import {DemoUtilsModule} from '../demo-utils/module';
import {FormsModule} from '@angular/forms';
import {NgbDropdownModule, NgbModalModule} from '@ng-bootstrap/ng-bootstrap';
import {FlatpickrModule} from 'angularx-flatpickr';
import {MatRadioModule} from '@angular/material/radio';
import {CreateAppointmentDialog} from './create-appointment/create-appointment-dialog';
import {ViewAppointmentDialog} from './view-appointment/view-appointment-dialog';
import {AppointmentsListComponent} from './appointments-list/appointments-list.component';
import {MatSortModule} from "@angular/material/sort";

@NgModule({
    schemas: [NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA],
    declarations: [AppointmentsCalendarComponent, AppointmentsListComponent, CreateAppointmentDialog, ViewAppointmentDialog],
    imports: [
        CommonModule,
        AppointmentsRoutingModule,
        SharedModule,
        FormsModule,
        NgbModalModule,
        FlatpickrModule.forRoot(),
        CalendarModule.forRoot({
            provide: DateAdapter,
            useFactory: adapterFactory,
        }),
        DemoUtilsModule,
        NgbDropdownModule,
        MatRadioModule,
        MatSortModule
    ],
    entryComponents: []
})
export class AppointmentsModule {
}
