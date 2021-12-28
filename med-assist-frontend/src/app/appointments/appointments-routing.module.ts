import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {LayoutComponent} from '../shared/layout/layout.component';
import {AppointmentsListComponent} from "./appointments-list/appointments-list.component";

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            {path: '', component: AppointmentsListComponent},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AppointmentsRoutingModule {
}
