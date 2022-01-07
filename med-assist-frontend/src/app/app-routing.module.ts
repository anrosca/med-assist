import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {AuthGuard} from './core/guards/auth.guard';
import {AdminGuard} from './core/guards/admin.guard';
import {UsersModule} from './users/users.module';
import {DoctorsModule} from './doctors/doctors.module';
import {AccountModule} from './account/account.module';
import {AboutModule} from './about/about.module';
import {AppointmentsModule} from './appointments/appointments.module';
import {PatientsModule} from './patients/patients.module';
import {TreatmentsModule} from './treatments/treatments.module';
import {StatisticsModule} from './statistics/statistics.module';

const appRoutes: Routes = [
    {
        path: 'auth',
        loadChildren: () => import('./auth/auth.module').then(x => x.AuthModule)
    },
    {
        path: 'appointments',
        loadChildren: () => import('./appointments/appointments.module').then(x => AppointmentsModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'users',
        loadChildren: () => import('./users/users.module').then(x => UsersModule),
        canActivate: [AdminGuard]
    },
    {
        path: 'doctors',
        loadChildren: () => import('./doctors/doctors.module').then(x => DoctorsModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'patients',
        loadChildren: () => import('./patients/patients.module').then(x => PatientsModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'treatments',
        loadChildren: () => import('./treatments/treatments.module').then(x => TreatmentsModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'statistics',
        loadChildren: () => import('./statistics/statistics.module').then(x => StatisticsModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'account',
        loadChildren: () => import('./account/account.module').then(x => AccountModule),
        canActivate: [AuthGuard]
    },
    {
        path: 'about',
        loadChildren: () => import('./about/about.module').then(x => AboutModule),
        canActivate: [AuthGuard]
    },
    {
        path: '**',
        redirectTo: 'appointments/calendar',
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes)
    ],
    exports: [RouterModule],
    providers: []
})
export class AppRoutingModule {
}
