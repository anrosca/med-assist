import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {ProfileComponent} from './profile/profile.component';
import {LayoutComponent} from '../shared/layout/layout.component';
import {ProfileDetailsComponent} from './profile-details/profile-details.component';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            {path: 'profile', component: ProfileComponent},
            {path: 'profileDetails', component: ProfileDetailsComponent},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AccountRoutingModule {
}
