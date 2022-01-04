import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import { TreatmentsListComponent } from './treatments-list/treatments-list.component';
import {TreatmentsRoutingModule} from "./treatments-routing.module";
import {MatSortModule} from "@angular/material/sort";
import {PatientsModule} from "../patients/patients.module";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        TreatmentsRoutingModule,
        MatSortModule,
        PatientsModule
    ],
    declarations: [TreatmentsListComponent]
})
export class TreatmentsModule {
}
