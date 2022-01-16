import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import {PatientListComponent} from './patient-list/patient-list.component';
import {PatientsRoutingModule} from './patients-routing.module';
import {ViewPatientDialog} from './view-patient/view-patient-dialog';
import {DentalChartComponent, TeethPrinter} from './view-patient/dental-chart/dental-chart.component';
import {MatSortModule} from '@angular/material/sort';
import {AddToothTreatmentDialog} from './view-patient/add-tooth-treatment-dialog/add-tooth-treatment-dialog';
import {AddTreatmentDialog} from './view-patient/add-treatment-dialog/add-treatment-dialog';
import {NgxDropzoneModule} from "ngx-dropzone";
import {MatGridListModule} from "@angular/material/grid-list";
import { CreatePatientDialog } from './create-patient/create-patient-dialog';
import {FlatpickrModule} from "angularx-flatpickr";
import {CalendarModule, DateAdapter} from "angular-calendar";
import {adapterFactory} from "angular-calendar/date-adapters/date-fns";
import {NgxMatSelectSearchModule} from "ngx-mat-select-search";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        PatientsRoutingModule,
        MatSortModule,
        NgxDropzoneModule,
        MatGridListModule,
        CalendarModule.forRoot({
            provide: DateAdapter,
            useFactory: adapterFactory,
        }),
        FlatpickrModule.forRoot(),
        NgxMatSelectSearchModule,
    ],
    exports: [
        TeethPrinter
    ],
    declarations: [PatientListComponent, ViewPatientDialog, DentalChartComponent, TeethPrinter, AddToothTreatmentDialog, AddTreatmentDialog, CreatePatientDialog]
})
export class PatientsModule {
}
