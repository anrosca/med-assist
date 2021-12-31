import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import {PatientListComponent} from "./patient-list/patient-list.component";
import {PatientsRoutingModule} from "./patients-routing.module";
import { ViewPatientDialog } from './view-patient/view-patient-dialog';
import { DentalChartComponent } from './view-patient/dental-chart/dental-chart.component';

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        PatientsRoutingModule
    ],
    declarations: [PatientListComponent, ViewPatientDialog, DentalChartComponent]
})
export class PatientsModule {
}
