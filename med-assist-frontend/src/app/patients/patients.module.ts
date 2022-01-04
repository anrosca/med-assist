import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import {PatientListComponent} from "./patient-list/patient-list.component";
import {PatientsRoutingModule} from "./patients-routing.module";
import { ViewPatientDialog } from './view-patient/view-patient-dialog';
import {DentalChartComponent, TeethPrinter} from './view-patient/dental-chart/dental-chart.component';
import {MatSortModule} from "@angular/material/sort";
import { AddToothTreatmentDialog } from './view-patient/add-tooth-treatment-dialog/add-tooth-treatment-dialog';
import { AddTreatmentDialog } from './view-patient/add-treatment-dialog/add-treatment-dialog';

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        PatientsRoutingModule,
        MatSortModule
    ],
    exports: [
        TeethPrinter
    ],
    declarations: [PatientListComponent, ViewPatientDialog, DentalChartComponent, TeethPrinter, AddToothTreatmentDialog, AddTreatmentDialog]
})
export class PatientsModule {
}
