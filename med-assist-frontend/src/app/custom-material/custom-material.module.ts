import {LOCALE_ID, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {MatMomentDateModule} from '@angular/material-moment-adapter';

import {SelectCheckAllComponent} from './select-check-all/select-check-all.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatIconModule} from '@angular/material/icon';
import {MatCardModule} from '@angular/material/card';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatTableModule} from '@angular/material/table';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatChipsModule} from '@angular/material/chips';
import {MatBadgeModule} from '@angular/material/badge';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatTabsModule} from '@angular/material/tabs';
import {MatDialogModule} from '@angular/material/dialog';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSelectModule} from '@angular/material/select';
import {MatListModule} from '@angular/material/list';
import {MAT_DATE_FORMATS} from '@angular/material/core';
import {MatMenuModule} from '@angular/material/menu';

export const MY_FORMATS = {
    parse: {
        dateInput: 'DD MMM YYYY',
    },
    display: {
        dateInput: 'DD MMM YYYY',
        monthYearLabel: 'MMM YYYY',
        dateA11yLabel: 'LL',
        monthYearA11yLabel: 'MMMM YYYY'
    }
};

@NgModule({
    imports: [
        CommonModule,
        MatMomentDateModule,
        MatSidenavModule, MatIconModule, MatToolbarModule, MatButtonModule,
        MatListModule, MatCardModule, MatProgressBarModule, MatInputModule,
        MatSnackBarModule, MatMenuModule, MatProgressSpinnerModule, MatDatepickerModule,
        MatAutocompleteModule, MatTableModule, MatDialogModule, MatTabsModule,
        MatTooltipModule, MatSelectModule, MatPaginatorModule, MatChipsModule,
        MatButtonToggleModule, MatSlideToggleModule, MatBadgeModule, MatCheckboxModule,
        MatExpansionModule, DragDropModule
    ],
    exports: [
        CommonModule,
        MatSidenavModule, MatIconModule, MatToolbarModule, MatButtonModule,
        MatListModule, MatCardModule, MatProgressBarModule, MatInputModule,
        MatSnackBarModule, MatMenuModule, MatProgressSpinnerModule, MatDatepickerModule,
        MatAutocompleteModule, MatTableModule, MatDialogModule, MatTabsModule,
        MatTooltipModule, MatSelectModule, MatPaginatorModule, MatChipsModule,
        MatButtonToggleModule, MatSlideToggleModule, MatBadgeModule, MatCheckboxModule,
        MatExpansionModule, SelectCheckAllComponent, DragDropModule
    ],
    providers: [
        {
            provide: MAT_DATE_FORMATS,
            useValue: MY_FORMATS
        },
        {provide: LOCALE_ID, useValue: 'en-gb'}
    ],
    declarations: [SelectCheckAllComponent]
})
export class CustomMaterialModule {
    static forRoot() {
        return {
            ngModule: CustomMaterialModule,
            providers: []
        };
    }
}
