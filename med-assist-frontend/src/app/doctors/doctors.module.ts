import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import {DoctorListComponent} from './doctor-list/doctor-list.component';
import {DoctorsRoutingModule} from './doctors-routing.module';

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        DoctorsRoutingModule
    ],
    declarations: [DoctorListComponent]
})
export class DoctorsModule {
}
