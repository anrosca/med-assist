import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {CalendarModule} from 'angular-calendar';
import {CalendarHeaderComponent} from './calendar-header.component';
import {CustomMaterialModule} from '../custom-material/custom-material.module';

@NgModule({
    imports: [CommonModule, FormsModule, CalendarModule, CustomMaterialModule],
    declarations: [CalendarHeaderComponent],
    exports: [CalendarHeaderComponent],
})
export class DemoUtilsModule {
}
