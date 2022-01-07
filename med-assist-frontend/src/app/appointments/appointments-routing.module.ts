import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {LayoutComponent} from '../shared/layout/layout.component';
import {AppointmentsCalendarComponent} from './appointments-calendar/appointments-calendar.component';
import {AppointmentsListComponent} from './appointments-list/appointments-list.component';

const routes: Routes = [
    {
        path: 'calendar',
        component: LayoutComponent,
        children: [
            {path: '', component: AppointmentsCalendarComponent},
        ]
    },
    {
        path: 'list',
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
