import {CommonModule} from '@angular/common';

import {DashboardRoutingModule} from './dashboard-routing.module';
import {SharedModule} from '../shared/shared.module';
import {CreateAppointmentDialog, DashboardHomeComponent} from './dashboard-home/dashboard-home.component';
import {CalendarModule, DateAdapter} from "angular-calendar";
import {adapterFactory} from "angular-calendar/date-adapters/date-fns";
import {CUSTOM_ELEMENTS_SCHEMA, NgModule, NO_ERRORS_SCHEMA} from "@angular/core";
import {DemoUtilsModule} from "../demo-utils/module";
import {FormsModule} from "@angular/forms";
import {NgbDropdownModule, NgbModalModule} from "@ng-bootstrap/ng-bootstrap";
import {FlatpickrModule} from "angularx-flatpickr";
import {MatRadioModule} from "@angular/material/radio";



@NgModule({
    schemas: [NO_ERRORS_SCHEMA, CUSTOM_ELEMENTS_SCHEMA],
    declarations: [DashboardHomeComponent, CreateAppointmentDialog],
    imports: [
        CommonModule,
        DashboardRoutingModule,
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
        MatRadioModule
    ],
    entryComponents: []
})
export class DashboardModule {
}
