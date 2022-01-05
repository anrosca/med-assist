import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {LayoutComponent} from '../shared/layout/layout.component';
import {StatisticsViewComponent} from './statistics-view/statistics-view.component';

const routes: Routes = [
    {
        path: '',
        component: LayoutComponent,
        children: [
            {path: '', component: StatisticsViewComponent},
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class StatisticsRoutingModule {
}
