import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {AuthGuard} from './core/guards/auth.guard';
import {AdminGuard} from './core/guards/admin.guard';
import {DashboardModule} from "./dashboard/dashboard.module";
import {UsersModule} from "./users/users.module";
import {DoctorsModule} from "./doctors/doctors.module";
import {AccountModule} from "./account/account.module";
import {AboutModule} from "./about/about.module";

const appRoutes: Routes = [
    {
        path: 'auth',
        loadChildren: () => import('./auth/auth.module').then(x => x.AuthModule)
    },
    {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(x => DashboardModule),
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
        redirectTo: 'dashboard',
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
