import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../shared/shared.module';
import { StatisticsViewComponent } from './statistics-view/statistics-view.component';
import {StatisticsRoutingModule} from "./statistics-routing.module";

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        StatisticsRoutingModule
    ],
    declarations: [StatisticsViewComponent]
})
export class StatisticsModule {
}
