import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import {AppointmentsListComponent} from "./appointments-list/appointments-list.component";
import {AppointmentsRoutingModule} from "./appointments-routing.module";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        AppointmentsRoutingModule
    ],
    declarations: [AppointmentsListComponent]
})
export class AppointmentsModule {
}
